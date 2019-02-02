package model;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class UserModel {

    private String uid; //유저가 로그인 할 때, 소켓, uid를 넣어준다.
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public UserModel(String uid, Socket socket, BufferedReader in, PrintWriter out) {
        this.uid = uid;
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

}
