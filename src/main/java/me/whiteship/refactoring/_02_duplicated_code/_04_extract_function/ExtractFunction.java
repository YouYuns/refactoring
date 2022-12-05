package me.whiteship.refactoring._02_duplicated_code._04_extract_function;

import me.whiteship.refactoring._06_mutable_data._19_separate_query_from_modifier.Invoice;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ExtractFunction {
    public void printOwing(Invoice invoice){

        printBanner();

        Integer outstanding = calculateOutstanding(invoice);
        recordDueDate(invoice);
        printDetails(invoice, outstanding);

    }

    private static Integer calculateOutstanding(Invoice invoice) {
        //미해결 채무(outstanding)를 계산한다.
        Integer outstanding = 0;
        for (Order order : invoice.getOrders()) {
            outstanding += order.amount;
        }
        return outstanding;
    }

    private static void recordDueDate(Invoice invoice) {
        //마감일(dueDate)을 기록한다.
        LocalDate today = LocalDate.now();
        invoice.setDueDate(today);
    }

    private static void printDetails(Invoice invoice, Integer outstanding) {
        //세부사항을 출력한다.
        System.out.println("고객명 :" + invoice.getCustomer());
        System.out.println("채무액 :" + outstanding);
        System.out.println("마감일 :" + invoice.getDueDate().toString());
    }

    private static void printBanner() {
        System.out.println("-------------------------");
        System.out.println("----------고객채무---------");
        System.out.println("-------------------------");
    }
}
