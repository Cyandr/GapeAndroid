package com.gape.ide.working;

/**
 * Created by cyandr on 2017/1/5.
 */
public class GParser {

    static int FLOOR = 0;
    String m_ins_gtring;

    void parse(String ins_gtring) {
        int gtring_length = ins_gtring.length();

        for (int i = 0; i <= gtring_length - 1; i++) {
            char c = ins_gtring.charAt(i);
            if (c == '{') {

                FLOOR++;

            }
            if (c == '矩') {
                String cc = ins_gtring.substring(i, i + 2);
                if (cc.equals("矩形框")) {


                }

            }
            if (c == '}') {

                FLOOR--;
            }


        }
    }
}
