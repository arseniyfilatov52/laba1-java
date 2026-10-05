import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Работа с матрицами комплексных чисел");

        while (true) {
            System.out.println("\nВыберите операцию:");
            System.out.println("1. Сложение (Matrix 1 + Matrix 2)");
            System.out.println("2. Вычитание (Matrix 1 - Matrix 2)");
            System.out.println("3. Умножение (Matrix 1 * Matrix 2)");
            System.out.println("4. Деление (Matrix 1 / Matrix 2)");
            System.out.println("5. Транспонировать матрицу");
            System.out.println("6. Определитель матрицы");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Выход из программы.");
                return;
            }

            try {
                if (choice >= 1 && choice <= 6) {
                    System.out.println("Введите размеры первой матрицы:");
                    System.out.print("Строки: ");
                    int r1 = scanner.nextInt();
                    System.out.print("Столбцы: ");
                    int c1 = scanner.nextInt();
                    ComplexMatrix m1 = readMatrix(scanner, r1, c1, "первой");

                    ComplexMatrix m2 = null;
                    if (choice >= 1 && choice <= 4) {
                        System.out.println("Введите размеры второй матрицы:");
                        System.out.print("Строки: ");
                        int r2 = scanner.nextInt();
                        System.out.print("Столбцы: ");
                        int c2 = scanner.nextInt();
                        if (choice == 1 || choice == 2) {
                            if (r1 != r2 || c1 != c2) {
                                throw new RuntimeException("Размеры матриц для сложения/вычитания должны совпадать");
                            }
                        } else if (choice == 3) {
                            if (c1 != r2) {
                                throw new RuntimeException("Число столбцов первой матрицы должно быть равно числу строк второй");
                            }
                        } else if (choice == 4) {
                            if (r2 != c2) {
                                throw new RuntimeException("Для деления вторая матрица должна быть квадратной");
                            }
                            if (c1 != r2) {
                                throw new RuntimeException("Недопустимые размеры матриц для деления");
                            }
                        }
                        m2 = readMatrix(scanner, r2, c2, "второй");
                    }

                    switch (choice) {
                        case 1:
                            System.out.println("Результат сложения:");
                            printMatrix(m1.add(m2));
                            break;
                        case 2:
                            System.out.println("Результат вычитания:");
                            printMatrix(m1.subtract(m2));
                            break;
                        case 3:
                            System.out.println("Результат умножения:");
                            printMatrix(m1.multiply(m2));
                            break;
                        case 4:
                            System.out.println("Результат деления:");
                            printMatrix(m1.divide(m2));
                            break;
                        case 5:
                            System.out.println("Результат транспонирования:");
                            printMatrix(m1.transpose());
                            break;
                        case 6:
                            System.out.println("Определитель матрицы: " + m1.determinant());
                            break;
                    }
                } else {
                    System.out.println("Неверный выбор, попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка выполнения операции: " + e);
            }
        }
    }

    public static ComplexMatrix readMatrix(Scanner scanner, int rows, int cols, String matrixName) {
        ComplexMatrix matrix = new ComplexMatrix(rows, cols);
        System.out.println("Введите элементы " + matrixName + " матрицы (действительная и мнимая часть через пробел):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("[" + i + "][" + j + "] действительная мнимая: ");
                double real = scanner.nextDouble();
                double imag = scanner.nextDouble();
                matrix.setElement(i, j, new Complex(real, imag));
            }
        }
        return matrix;
    }

    public static void printMatrix(ComplexMatrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                System.out.print(matrix.getElement(i, j) + "   ");
            }
            System.out.println();
        }
    }
}
