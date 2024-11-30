## Bytecode Interpreter

With the motive of learning the workings of bytecode interpreter, showcasing different virtual machine architectures and techniques for executing bytecode instructions.

### Files Overview

1. **`basic_accumulator.c`**  
   Implements a bytecode interpreter using an **accumulator-based architecture**.  
   - Single register (`accumulator`) to hold intermediate and final results.
   - Operations include addition, subtraction, and immediate value handling.

2. **`basic_incrementer.c`**  
   Demonstrates a simple interpreter focused on incrementing and decrementing values.  
   - Minimalist design to process basic arithmetic bytecode.

3. **`register_machine.c`**  
   Implements a **register-based virtual machine** with multiple registers.  
   - Supports arithmetic operations (add, subtract, multiply, divide) across registers.
   - Encodes and decodes instructions with multiple operands.

4. **`stack_machine.c`**  
   Implements a **stack-based virtual machine**.  
   - Uses a stack to evaluate expressions.
   - Operations include push, pop, and arithmetic operations.

### Features
- Simple examples of core architectures for bytecode interpretation.
- Includes error handling (e.g., division by zero, unknown opcodes).
- Lightweight implementations for educational and practical purposes.

### How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your_username/bytecode-interpreter.git
   cd bytecode-interpreter
   ```
2. Compile a specific file (e.g., accumulator.c):
  ```bash
  gcc basic_accumulator.c -o accumulator
  ```
3. Run the executable:
  ```bash
  ./accumulator
  ```
### Acknowledgement
Home-grown bytecode interpreters by Vladimir Kazanov <br>
https://medium.com/bumble-tech/home-grown-bytecode-interpreters-51e12d59b25c
