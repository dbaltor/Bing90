package online.dbaltor;

public class Bingo90 {


    public static void main(String[] args) {
        var numberOfStrips = 1;
        if (args.length > 0) {
            try {
                numberOfStrips = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("####################################################################");
                System.out.println("The input is optional. If informed, it must be the number of strips.");
                System.out.println("####################################################################");
                System.exit(1);
            }
        }

        var ticketGenerator = new TicketGenerator(new Randomizer());
        System.out.println(ticketGenerator.generate(numberOfStrips));
    }
}
