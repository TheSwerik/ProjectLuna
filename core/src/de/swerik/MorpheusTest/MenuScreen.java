package de.swerik.MorpheusTest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import static de.swerik.MorpheusTest.Main.WIDTH;
import static de.swerik.MorpheusTest.Main.HEIGHT;

public class MenuScreen extends AbstractScreen {
    private SpriteBatch batch;
    private Camera camera;

    private BitmapFont font;

    private Skin skin;
    private Stage stage;

    private Label messageReceived;
    private Label myIP;
    private TextArea ip;
    private TextArea msg;
    private TextButton button;
    private String ipAdress;

    public MenuScreen(Game game) {
        super(game);
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        batch = new SpriteBatch();
        font = new BitmapFont();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface i : Collections.list(interfaces)) {
                for (InetAddress addr : Collections.list(i.getInetAddresses())) {
                    if (addr instanceof Inet4Address) {
                        ipAdress = ipAdress + addr.getHostAddress() + "\n";
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-4);
        }

        Group group = new Group();
        group.setBounds(0, 0, WIDTH, HEIGHT);

        messageReceived = new Label("TesterTest", skin);
        myIP = new Label(ipAdress, skin);
        ip = new TextArea("", skin);
        msg = new TextArea("", skin);
        button = new TextButton("Senden", skin);
        messageReceived.setPosition(WIDTH/2f, 100);
        myIP.setPosition(WIDTH/2f, 200);
        ip.setPosition(WIDTH/2f, 300);
        msg.setPosition(WIDTH/2f, 400);
        button.setPosition(WIDTH/2f, 500);

        group.addActor(messageReceived);
        group.addActor(myIP);
        group.addActor(ip);
        group.addActor(msg);
        group.addActor(button);

        stage.addActor(group);

        stage.getCamera().position.set(WIDTH / 2f, HEIGHT / 2f, 0f);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String send = "";
                if (msg.getText().length() == 0) {
                    send = "Das ist ein Test. \n";
                } else {
                    send = msg.getText() + "\n";
                }
                if (ip.getText().length() == 0) {
                    return;
                }

                SocketHints sh = new SocketHints();
                sh.connectTimeout = 10000;

                Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, ip.getText(), 1337, sh);
                try {
                    socket.getOutputStream().write(send.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-2);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocketHints ssh = new ServerSocketHints();
                ssh.acceptTimeout = 0;
                ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 1337, ssh);
                while (true) {
                    Socket s = serverSocket.accept(null);
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(s.getInputStream()));

                    try {
                        messageReceived.setText(buffer.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(-3);
                    }
                }
            }
        }).start();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(165f, 0f, 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font.setColor(Color.WHITE);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        stage.draw();
        font.draw(batch, "press to start", WIDTH / 2f - 20f, HEIGHT / 3f * 2f);
        batch.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
        Gdx.app.exit();
        System.exit(0);
    }
}
