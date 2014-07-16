import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by demo4 on 16.07.14.
 */
public class HttpServer {
    public static void run(int port, String dir){
        try(ServerSocket serv = new ServerSocket(port);) {
            while(true){
                Socket client = serv.accept();
                new Thread(new HttpClientHandler(client, dir)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class HttpClientHandler implements Runnable{

        Socket socket;
        String dir;
        Map<String, Runnable> commands = new HashMap<>();

        public HttpClientHandler(Socket s, String path){
            socket = s;
            dir = path;
        }

        @Override
        public void run() {
            InputStream in;
            OutputStream out;

            try( Socket tmp = this.socket ){
                in = socket.getInputStream();
                out = socket.getOutputStream();
                //читаем первую строку запроса, игнорируем все заголовки которые идут дальше первой строки
                StringBuilder sb = new StringBuilder();
                int c;
                while((c =in.read())!=-1 && c!=10 && c!=13){
                    sb.append((char)c);
                }
                //получаем команду и ее аргументы
                String data = sb.toString();
                String args[] = data.split(" ");
                String cmd = args[0].trim().toUpperCase();
                // пишем ответ Hello world
                String s = "<html><title>test</title><body>Hello <b>world</b></body></html>";
                //пишем статус ответа
                out.write("HTTP/1.0 200 OK\r\n".getBytes());
                //минимально необходимые заголовки, тип и длина
                out.write("Content-Type: text/html\r\n".getBytes());
                out.write(("Content-Length: "+s.length()+"\r\n").getBytes());
                //пустая строка отделяет заголовки от тела
                out.write("\r\n".getBytes());
                //тело
                out.write(s.getBytes());
                out.flush();

            }catch (Exception e){
                //
            }
        }
    }

    interface CommandHandler{
        public void HandleCommand(File f, OutputStream os) throws IOException;
    }

    static class GetHandler implements CommandHandler {

        @Override
        public void HandleCommand(File f, OutputStream os) throws IOException {
            
        }
    }
}

