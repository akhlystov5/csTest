package com.bc;

import java.util.*;
import java.util.stream.*;

public class Solution {
    public static final String PIPE = "\\|";
    public static final String COMMA = ",";
    public static final String COLON = ":";

    static class Transaction {
        String ticker;
        String quantity;
        String type;

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "[" + getType() + ", " + getTicker() + ", " + getQuantity() + "]";
        }
    }
    static class Holding {
        String name;
        String ticker;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public int getShares() {
            return shares;
        }

        public void setShares(int shares) {
            this.shares = shares;
        }

        int shares;

        Holding(String string) {
            String[]array = string.split(COMMA);
            ticker = array[0];
            name = array[1];
            shares = Integer.parseInt(array[2]);
        }

        @Override
        public String toString() {
            return "[" + getTicker() + ", " + getName() + ", " + getShares() + "]";
        }
    }

    /*
     * Complete the function below.
     */
    static int countHoldings(String input) {
        List<String> list = Arrays.asList(input.split(PIPE));
        List<Holding> holdings = list.stream().map(Holding::new).collect(Collectors.toList());
        return holdings.stream().mapToInt(Holding::getShares).sum();
    }

    static String printHoldings(String portfolioString) {
        List<String> list = Arrays.asList(portfolioString.split(PIPE));
        List<Holding> holdings = list.stream().map(Holding::new).sorted(Comparator.comparing(Holding::getTicker)).collect(Collectors.toList());
//        holdings.sort(Comparator.comparing(Holding::getTicker));
        String result = holdings.stream().map(Holding::toString).collect(Collectors.joining(", "));
        return result;
    }

    static String generateTransactions(String input) {
        String[] array = input.split(COLON);
        List<String> list = Arrays.asList(array[0].split(PIPE));
        List<Holding> portfolios = list.stream().map(Holding::new).sorted(Comparator.comparing(Holding::getTicker)).collect(Collectors.toList());

        list = Arrays.asList(array[1].split(PIPE));
        List<Holding> benchmarks = list.stream().map(Holding::new).sorted(Comparator.comparing(Holding::getTicker)).collect(Collectors.toList());

        List<Transaction> transactions = new ArrayList<>();
        for (int i=0; i < portfolios.size();i++) {
            Holding portfolio = portfolios.get(i);
            Holding benchmark = benchmarks.get(i);
            Transaction tr = new Transaction();
            if (portfolio.getShares() == benchmark.getShares())
                continue;
            tr.setTicker(portfolio.getTicker());

            if (portfolio.getShares() > benchmark.getShares()) {
                tr.setQuantity((portfolio.getShares() - benchmark.getShares())+".00");
                tr.setType("SELL");
            } else {
                tr.setQuantity(""+(benchmark.getShares() - portfolio.getShares())+".00");
                tr.setType("BUY");
            }
            transactions.add(tr);
        }
        String result = transactions.stream().map(Transaction::toString).collect(Collectors.joining(", "));
        return result;
    }

    public static void main(String []args){
        int value = countHoldings("VOD,Vodafone,10|GOOG,Google,15|MSFT,Microsoft,12");
        System.out.println(value);

        String str = printHoldings("VOD,Vodafone,10|GOOG,Google,15|MSFT,Microsoft,12");
        System.out.println(str);

        String result = generateTransactions("VOD,Vodafone,10|GOOG,Google,15|MSFT,Microsoft,12" +
                                                  ":VOD,Vodafone,16|GOOG,Google,10|MSFT,Microsoft,25");
        System.out.println(result);


    }
}

