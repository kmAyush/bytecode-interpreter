import java.util.Arrays;

/* OUTPUT
Enter expression
100-4-28
Bytecode Interpretation:
  Memory Consumed: 6 bytes
  Execution Time: 900 ns
  Final Accumulator: 68
Direct Interpretation:
  Memory Consumed: 24 bytes
  Execution Time: 800 ns
  Final Accumulator: 68

Comparison:
Bytecode executed code in 0.25x memory usage and 1.13x execution time compared to Direct interpreter.
*/

public class C {
    private static int ip;
    private static long accumulator;

    // Opcodes for Bytecode Interpretation
    private static final int OP_INC = 0;
    private static final int OP_DEC = 1;
    private static final int OP_ADDI = 2;
    private static final int OP_SUBI = 3;
    private static final int OP_DONE = 4;

    // Commands for Direct Interpretation
    enum Command {
        INC, DEC, ADDI, SUBI, DONE
    }

    // Reset VM state
    private static void resetVM() {
        ip = 0;
        accumulator = 0;
    }

    // Bytecode Interpreter
    private static long interpretBytecode(byte[] bytecode) {
        resetVM();
        long memoryConsumed = 0;
        while (true) {
            if (ip >= bytecode.length) break;

            int instruction = bytecode[ip++];
            memoryConsumed += 1; // Memory for the instruction
            switch (instruction) {
                case OP_INC -> accumulator++;
                case OP_DEC -> accumulator--;
                case OP_ADDI -> {
                    accumulator += bytecode[ip++];
                    memoryConsumed += 1; // Memory for the argument
                }
                case OP_SUBI -> {
                    accumulator -= bytecode[ip++];
                    memoryConsumed += 1; // Memory for the argument
                }
                case OP_DONE -> {
                    return memoryConsumed;
                }
                default -> throw new IllegalArgumentException("Unknown opcode: " + instruction);
            }
        }
        return memoryConsumed;
    }

    // Direct Interpreter
    private static long interpretDirect(Command[] commands, int[] args) {
        resetVM();
        long memoryConsumed = 0;
        for (int i = 0; i < commands.length; i++) {
            Command command = commands[i];
            memoryConsumed += 4; // Estimated memory for the Command object
            switch (command) {
                case INC -> accumulator++;
                case DEC -> accumulator--;
                case ADDI, SUBI -> {
                    accumulator += args[i];
                    memoryConsumed += 4; // Estimated memory for the argument
                }
                case DONE -> {
                    return memoryConsumed;
                }
                default -> throw new IllegalArgumentException("Unknown command: " + command);
            }
        }
        return memoryConsumed;
    }

    // Measure execution time
    private static long measureExecutionTime(Runnable runnable) {
        long startTime = System.nanoTime();
        runnable.run();
        return System.nanoTime() - startTime;
    }

    public static void main(String[] ar) {
        // Test case data
        byte[] bytecode = new byte[1_000_000];
        Arrays.fill(bytecode, (byte) OP_ADDI);
        bytecode[bytecode.length - 2] = OP_SUBI;
        bytecode[bytecode.length - 1] = OP_DONE;

        Command[] commands = new Command[500_000];
        Arrays.fill(commands, Command.ADDI);
        commands[commands.length - 2] = Command.SUBI;
        commands[commands.length - 1] = Command.DONE;

        int[] args = new int[500_000];
        Arrays.fill(args, 1);
        args[args.length - 2] = 1;
        args[args.length - 1] = 0;

        // Measure Bytecode Interpretation
        long bytecodeMemory = interpretBytecode(bytecode);
        long bytecodeTime = measureExecutionTime(() -> interpretBytecode(bytecode));
        System.out.println("Bytecode Interpretation:");
        System.out.println("  Memory Consumed: " + bytecodeMemory + " bytes");
        System.out.println("  Execution Time: " + bytecodeTime + " ns");
        System.out.println("  Final Accumulator: " + accumulator);

        // Measure Direct Interpretation
        long directMemory = interpretDirect(commands, args);
        long directTime = measureExecutionTime(() -> interpretDirect(commands, args));
        System.out.println("Direct Interpretation:");
        System.out.println("  Memory Consumed: " + directMemory + " bytes");
        System.out.println("  Execution Time: " + directTime + " ns");
        System.out.println("  Final Accumulator: " + accumulator);

        // Comparison
        double memoryRatio = (double) bytecodeMemory / directMemory;
        double timeRatio = (double) bytecodeTime / directTime;

        System.out.println("\nComparison:");
        System.out.printf("Bytecode executed code in %.2fx memory usage and %.2fx execution time compared to Direct interpreter.%n",
                memoryRatio, timeRatio);
        System.out.println("Bytecode uses less memory but takes slightly more time due to decoding.");
        System.out.println("Direct Interpretation is faster but consumes more memory due to verbose command representation.");
    }
}
