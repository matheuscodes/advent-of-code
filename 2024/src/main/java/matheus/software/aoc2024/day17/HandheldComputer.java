package matheus.software.aoc2024.day17;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class HandheldComputer {

    long registerA;
    long registerB;
    long registerC;

    String console;



    static class JumpException extends Exception {
        int location;

        public JumpException(int operand) {
            super();
            location = operand;
        }
    }

    public String interpretProgram(String raw) {
        String[] split = raw.split("\\n\\n");
        String[] registers = split[0].split("\\n");
        String[] code = split[1]
                .replaceAll("\\n", "")
                .replaceAll("Program: ", "")
                .split(",");

        long a = Long.parseLong(registers[0].replaceAll("Register A: ", ""));

        List<Integer> program = Arrays.stream(code).map(Integer::parseInt).toList();
        executeProgram(a, program);
        return console;
    }

    public long findProgramRegister(String raw) {
        String[] code = raw.split("\\n\\n")[1]
                .replaceAll("\\n", "")
                .replaceAll("Program: ", "")
                .split(",");
        success = Long.MAX_VALUE;
        List<Integer> program = Arrays.stream(code).map(Integer::parseInt).toList();
        dfs(program, 0, 0);
        return success;
    }

    long success = Long.MAX_VALUE;
    private void dfs(final List<Integer> program, final long cur, int pos) {
        for (int i = 0; i < 8; i++) {
            long nextNum = (cur << 3) + i;
            List<Integer> execResult = executeProgram(nextNum, program);
            if (!execResult.equals(program.subList(program.size() - pos - 1, program.size()))) {
                continue;
            }
            if (pos == program.size() - 1) {
                success = Math.min(success, nextNum);
                return;
            }
            dfs(program, nextNum, pos + 1);
        }
    }

    private List<Integer> executeProgram(final long a, final List<Integer> code) {
        registerA = a;
        registerB = 0;
        registerC = 0;
        console = "";
        for (int i = 0; i < code.size(); i += 2) {
            try {
                execute(code.get(i), code.get(i + 1));
            } catch (JumpException e) {
                i = e.location - 2;
            }
        }
        return Arrays.stream(console.split(",")).map(Integer::parseInt).toList();
    }

    private void execute(final int command, final int operand) throws JumpException {
        switch (command) {
            case 0 -> adv(operand);
            case 1 -> bxl(operand);
            case 2 -> bst(operand);
            case 3 -> jnz(operand);
            case 4 -> bxc();
            case 5 -> out(operand);
            case 6 -> bdv(operand);
            case 7 -> cdv(operand);
            default -> throw new RuntimeException("Illegal Operation");
        }
    }

    private void adv(int operand) {
        long o = comboOperand(operand);
        registerA = (long) (registerA / Math.pow(2, o));
    }

    private void bxl(int operand) {
        long l = literalOperand(operand);
        registerB = registerB ^ l;
    }

    private void bst(int operand) {
        long o = comboOperand(operand);
        registerB = o % 8;
    }

    private void jnz(int operand) throws JumpException {
        if(registerA == 0) return;
        throw new JumpException(operand);
    }

    private void bxc() {
        registerB = registerB ^ registerC;
    }

    private void out(int operand) {
        long o = comboOperand(operand);
        if (console.length() == 0) {
            console += (o % 8);
        } else {
            console += "," + (o % 8);
        }
    }

    private void bdv(int operand) {
        long o = comboOperand(operand);
        registerB = (long) (registerA / Math.pow(2, o));
    }

    private void cdv(int operand) {
        long o = comboOperand(operand);
        registerC = (long) (registerA / Math.pow(2, o));
    }

    private long comboOperand(final int operand) {
        return switch (operand) {
            case 0, 1, 2, 3 -> literalOperand(operand);
            case 4 -> registerA;
            case 5 -> registerB;
            case 6 -> registerC;
            default -> throw new RuntimeException("Invalid");
        };
    }

    private int literalOperand(final int operand) {
        return operand;
    }
}
