package task1628;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* 
Кто первый встал - того и тапки
1. Разберись, что делает программа.
1.1. Каждая нить должна читать с консоли строки. Используй готовый static BufferedReader reader.
1.2. Используй AtomicInteger readStringCount, чтобы посчитать, сколько строк уже считано с консоли всеми нитями.
2. Реализуй логику метода run:
2.1. Пока нить не прервана (!isInterrupted) читай с консоли строки и добавляй их в поле List<String> result.
2.2. Используй readStringCount для подсчета уже считанных с консоли строк.
2.3. Тело метода run нужно поместить в блок try-catch.


Requirements:
1. Метод run должен работать пока нить не прервана (!isInterrupted).
2. Метод run НЕ должен создавать свои InputStreamReader-ы или BufferedReader-ы.
3. Метод run должен считывать строки из reader и добавлять их в список result.
4. Метод run должен после каждого считывания увеличивать счетчик прочитанных строк readStringCount на 1.
5. Программа должна выводить данные, считанные каждым потоком.*/

public class Solution {
    public static volatile AtomicInteger readStringCount = new AtomicInteger(0);
    public static volatile BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        //read count of strings
        int count = Integer.parseInt(reader.readLine());

        //init threads
        ReaderThread consoleReader1 = new ReaderThread();
        ReaderThread consoleReader2 = new ReaderThread();
        ReaderThread consoleReader3 = new ReaderThread();

        consoleReader1.start();
        consoleReader2.start();
        consoleReader3.start();

        while (count > readStringCount.get()) {

        }

        consoleReader1.interrupt();
        System.out.println(consoleReader1.getName()+" inter "+consoleReader1.isInterrupted());
        consoleReader2.interrupt();
        System.out.println(consoleReader2.getName()+" inter "+consoleReader2.isInterrupted());
        consoleReader3.interrupt();
        System.out.println(consoleReader3.getName()+" inter "+consoleReader3.isInterrupted());
        System.out.println("#1:" + consoleReader1);
        System.out.println("#2:" + consoleReader2);
        System.out.println("#3:" + consoleReader3);

        reader.close();
        System.out.println(readStringCount);
    }

    public static class ReaderThread extends Thread {
        private List<String> result = new ArrayList<>();

        public void run() {
            try {

                while (!isInterrupted()){
                    result.add(reader.readLine());
                    readStringCount.incrementAndGet();
                    System.out.println(Thread.currentThread().getName());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return result.toString();
        }
    }
}
