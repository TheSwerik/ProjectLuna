package de.swerik.MorpheusTest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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

        Group group = new Group();
        group.setBounds(0, 0, WIDTH, HEIGHT);

        messageReceived = new Label("TesterTest", skin);
        myIP = new Label(ipAdress, skin);
        ip = new TextArea("", skin);
        msg = new TextArea("", skin);
        button = new TextButton("Senden", skin);
        messageReceived.setPosition(100, 100);
        myIP.setPosition(100, 150);
        ip.setPosition(100, 200);
        msg.setPosition(100, 250);
        button.setPosition(100, 300);

        group.addActor(messageReceived);
        group.addActor(myIP);
        group.addActor(ip);
        group.addActor(msg);
        group.addActor(button);

        stage.addActor(group);

        stage.getCamera().position.set(WIDTH / 2f, HEIGHT / 2f, 0f);
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
