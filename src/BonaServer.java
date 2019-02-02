import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import model.MessageModel;
import model.UserModel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class BonaServer {

    static MessageModel messageModel;
    public static final ArrayList<UserModel> users = new ArrayList<>();

    public static void main(String[] args){

        System.out.println("Waiting for clients...");
        try {
            ServerSocket serverSocket = new ServerSocket(2546);
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection established");
                ConversationHandler handler = new ConversationHandler(socket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class ConversationHandler extends Thread {

    private Socket socket;
    private BufferedReader in; //서버에서 읽을 것들
    private PrintWriter out; //서버에서 쓸 것들

    public ConversationHandler(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();

        try {

            String uid = in.readLine();
            BonaServer.users.add(new UserModel(uid, socket, in, out));
            System.out.println(uid + " is logged in");

            while(true) {
                //@로그인 로직 - 유저의 uid와 room_no를 하나하나씩 다 받아온다.

                    String[] messageFromClient = in.readLine().split("////@@@////");

                    String profile = messageFromClient[0];
                    String nickname = messageFromClient[1];
                    String message = messageFromClient[2];
                    String timestamp = messageFromClient[3];
                    String current_uid = messageFromClient[4];
                    String destination_uid = messageFromClient[5];
                    String room_no = messageFromClient[6];
                    String image_encoded = messageFromClient[7];


//                System.out.println("index 0 - " + profile);
//                System.out.println("index 1 - " + nickname);
//                System.out.println("index 2 - " + message);
//                System.out.println("index 3 - " + timestamp);
//                System.out.println("index 4 - " + current_uid);
//                System.out.println("index 5 - " + destination_uid);
//                System.out.println("index 6 - " + room_no);
//                System.out.println("index 7 - " + image_encoded);

                    BonaServer.messageModel = new MessageModel(profile, nickname, message, timestamp, current_uid, destination_uid, room_no, image_encoded);
                    if(!message.equals("image@@!!")) {
                        insertMessage(BonaServer.messageModel);
                    }


                    //@메시지 보내기 로직 - 이 받아온 메시지를 어떤 특정 로직을 통해서 '특정 destination uid'로 보내는 것을 지정해야한다.
                    for(UserModel user : BonaServer.users){
                        if(user.getUid().equals(destination_uid)) {
                            user.getOut().println(BonaServer.messageModel.getProfile()
                                    + "////@@@////" + BonaServer.messageModel.getNickname()
                                    + "////@@@////" + BonaServer.messageModel.getMessage()
                                    + "////@@@////" + BonaServer.messageModel.getTimestamp()
                                    + "////@@@////" + BonaServer.messageModel.getCurrent_uid()
                                    + "////@@@////" + BonaServer.messageModel.getDestination_uid()
                                    + "////@@@////" + BonaServer.messageModel.getRoom_no()
                                    + "////@@@////" + BonaServer.messageModel.getImage());
                        }
                    }




            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //데이터베이스에 메시지를 넣어줌 - mysql 연동해서
    private void insertMessage(MessageModel message){

        Connection connection = null;
        Statement statement = null;

        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://54.180.152.140:3306/bona";
        String user = "webuser";
        String password = "ab1234";

        try {
            Class.forName(JDBC_DRIVER);
            connection = (Connection) DriverManager.getConnection(DB_URL, user, password);
            System.out.println("MySQL Connection Complete!");
            statement = (Statement) connection.createStatement();

            //데이터 집어 넣기
            String sql = "INSERT INTO messages(chatroom_id, send, receive, message, date) VALUES("
                    + message.getRoom_no() + ","
                    + message.getCurrent_uid() + ","
                    + message.getDestination_uid() + ","
                    + "'" + message.getMessage() + "'" + ","
                    + "'" + message.getTimestamp() + "'"
                    + ");";

            System.out.println(message.getRoom_no());
            System.out.println(message.getCurrent_uid());
            System.out.println(message.getDestination_uid());
            System.out.println(message.getMessage());
            System.out.println(message.getTimestamp());
            System.out.println(message.getImage());

            if(statement.execute(sql)){
                System.out.println("Data Successfully inserted!");
            };


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }




}