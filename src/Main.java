import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static Scanner input_texto = new Scanner(System.in);
    public static String[][] produtos = new String[1000][2];
    public static int total = 0;
    public static double[][] vendas = new double[1000][3];
    public static int totalVendas = 0;

    public static void main(String[] args) {

        int opcao;
        Locale.setDefault(Locale.US);

        do {
            exibirMenu();
            opcao = input_texto.nextInt();
            input_texto.nextLine();
            limpa();

            switch (opcao) {
                case 1:registarProduto();break;
                case 2:editarProduto(); break;
                case 3:buscarProduto(); break;
                case 4:listarProduto();break;
                case 5:apagarProduto();break;
                case 6:registarVenda();break;
                case 7:listarVendas();break;

                case 0:System.out.println("A sair...");break;
                default:
                    System.out.println("--- OPÇÃO INVÁLIDA ---");
                    enter();
                    break;
            }
            aguardar(2000);
        } while (opcao != 0);
    }

    public static void exibirMenu() {
        limpa();
        System.out.println("==== MERCADO ====\n");
        System.out.println("1 - Registar produto.");
        System.out.println("2 - Editar produto.");
        System.out.println("3 - Buscar produto por baliza de preços.");
        System.out.println("4 - Listar todos os produtos.");
        System.out.println("5 - Apagar produto.");
        System.out.println("\n6 - Registar venda.");
        System.out.println("7 - Listar todas as vendas.\n");
        System.out.println("0 - Sair.\n");
        System.out.print("Opção: ");
    }
    public static void registarProduto() {
        System.out.print("Digite o nome do produto a ser registado: ");
        String nomeProduto = input_texto.nextLine();
        double precoProduto;
        do {
            System.out.print("Digite o preço deste produto: ");
            precoProduto = input_texto.nextDouble();

            if (precoProduto <= 0) {
                System.out.println("\nO preço deve ser maior que 0. Tente novamente.\n");
            }
        } while (precoProduto <= 0);

        if (produtoRegistado(nomeProduto)) {
            System.out.println("\nProduto já foi registado anteriormente.");
        } else {
            produtos[total][0] = nomeProduto;
            produtos[total][1] = String.valueOf(precoProduto);
            total++;
            System.out.println("\n--- SUCESSO ---");
        }
        enter();
    }
    public static boolean produtoRegistado(String nomeProduto) {
        for (int i = 0; i < total; i++) {
            if (produtos[i][0].equalsIgnoreCase(nomeProduto)) {
                return true;
            }
        }
        return false;
    }
    public static void listarProduto() {

        if (total == 0) {
            System.out.println("Não há produtos registados.");
        } else {
            for (int i = 0; i < total; i++) {
                System.out.println("(" + i + ") - {" + produtos[i][0] + "} [" + produtos[i][1] + " €]");
            }
        }
        enter();
    }
    public static void editarProduto() {
        int codigo;

        if (total == 0) {
            System.out.println("Não há produtos registados.");

        } else {
            for (int i = 0; i < total; i++) {
                System.out.println("(" +i+ ") - {" + produtos[i][0] + "} [" + produtos[i][1] + " €]");
            }

            System.out.println();

            System.out.print("Digite o código do produto a ser editado (0) até (" + (total - 1) + "): ");
            codigo = input_texto.nextInt();
            input_texto.nextLine();

            System.out.println();

            if (codigo >= 0 && codigo < total) {
                System.out.print("Digite o nome do produto a ser registado: ");
                produtos[codigo][0] = input_texto.nextLine();
                System.out.print("Digite o preço deste produto: ");
                produtos[codigo][1] = input_texto.nextLine();
                System.out.println("\nSUCESSO!");
            } else {
                System.out.println("CÓDIGO INVALIDO!");
            }
        }
        enter();
    }
    public static void buscarProduto() {
        if (total == 0) {
            System.out.println("Não há produtos registados.");
            enter();
            return;
        }

        System.out.print("Digite o valor mínimo de preço para a busca: ");
        double preco_min = input_texto.nextDouble();
        System.out.print("Digite o valor máximo de preço para a busca: ");
        double preco_max = input_texto.nextDouble();

        System.out.println();

        for (int i = 0; i < total; i++) {
            double precoProduto = Double.parseDouble(produtos[i][1]);
            if (precoProduto >= preco_min && precoProduto <= preco_max) {
                System.out.println("(" +i+ ") - {" + produtos[i][0] + "} [" + produtos[i][1] + " €]");
            }
        }
        enter();
    }
    public static void registarVenda() {
        int codigo;
        int quantidade;
        DecimalFormat df2 = new DecimalFormat("0.00");

        limpa();

        if (total == 0) {
            System.out.println("Não há produtos registados.");
        } else {
            for (int i = 0; i < total; i++) {
                System.out.println("(" + i + ") - {" + produtos[i][0] + "} [" + produtos[i][1] + " €]");
            }

            System.out.println();

            System.out.print("Digite o código do produto a ser vendido (0) e (" + (total - 1) + "): ");
            codigo = input_texto.nextInt();
            input_texto.nextLine();

            if (codigo >= 0 && codigo < total) {
                System.out.println("\n(" + codigo + ") - {" + produtos[codigo][0] + "} [" + produtos[codigo][1] + " €]\n");

                System.out.print("Digite a quantidade a ser vendida deste produto: ");
                quantidade = input_texto.nextInt();

                double precoProduto = Double.parseDouble(produtos[codigo][1]);
                double totalVenda = precoProduto * quantidade;
                System.out.println("\n(" + produtos[codigo][0] + ") x " + quantidade + " = [" + df2.format(totalVenda) + "€]");
                System.out.println("\nSUCESSO!");

                vendas[totalVendas][0] = codigo;
                vendas[totalVendas][1] = quantidade;
                vendas[totalVendas][2] = totalVenda;
                totalVendas++;
            } else {
                System.out.println("--- OPÇÃO INVÁLIDA ---");
            }
        }
        enter();
    }
    public static void listarVendas() {
        double saldoTotalVendas = 0.0;
        DecimalFormat df2 = new DecimalFormat("0.00");

        if (totalVendas == 0) {
            System.out.println("Não há vendas registadas.");
        } else {
            for (int i = 0; i < totalVendas; i++) {
                int codigoProduto = (int)vendas[i][0];
                String nomeProduto = produtos[codigoProduto][0];
                double quantidade = vendas[i][1];
                double totalVenda = vendas[i][2];

                System.out.println("(" + codigoProduto + ") - {" + nomeProduto + "} x " + quantidade + " = [" + df2.format(totalVenda) + "]");

                saldoTotalVendas += totalVenda;
            }
            System.out.println("\nSaldo total das vendas: (" + df2.format(saldoTotalVendas) + "€)");
        }
        enter();
    }
    public static void apagarProduto() {
        if (total == 0) {
            System.out.println("Não há produtos registados.");
            enter();
            return;
        }

        for (int i = 0; i < total; i++) {
            System.out.println("(" + i + ") - {" + produtos[i][0] + "} [" + produtos[i][1] + " €]");
        }
        System.out.println();
        System.out.print("Digite o código do produto a ser apagado (0) e (" + (total - 1) + "): ");
        int codigo = input_texto.nextInt();
        input_texto.nextLine();

        if (codigo >= 0 && codigo < total) {
            for (int i = codigo; i < total - 1; i++) {
                produtos[i][0] = produtos[i + 1][0];
                produtos[i][1] = produtos[i + 1][1];
            }
            total--;
            System.out.println("\nProduto apagado com sucesso.");
        } else {
            System.out.println("\nCÓDIGO INVÁLIDO!");
        }
        enter();
    }

    public static void limpa() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }
    public static void aguardar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void enter(){
        System.out.print("\nPressione ENTER para continuar...");
        input_texto.nextLine();
    }
}




