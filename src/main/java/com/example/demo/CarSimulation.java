package com.example.demo;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.concurrent.Semaphore;
import java.util.*;
class Car implements Runnable {
    private double x;
    private double y;
    private double width;
    private double height;
    private String dir;
    private String angle="straight";
    private String location;
    private Image  image;

    private static final int MAX_CARS = 1;
    private static final Semaphore semaphore = new Semaphore(MAX_CARS, true);

    private final int id;

    public Car(double x, double y,String dir,int id,String location) {
        this.x = x;
        this.y = y;
        this.dir=dir;
        this.id=id;
        this.location=location;

    }

    @Override
    public void run() {
        try {
            System.out.printf("Car %d is waiting to enter the intersection%n", id);

            System.out.printf("Car %d enters the intersection%n", id);
           if(location.equals("down"))
            {
                for(int i = 550; i>=420;i--)
                {
                    moveup();
                    Thread.sleep(10);
                }
                if(dir.equals("left"))
                {
                    for(int i = 429; i>=385;i--)
                    {
                        moveup();
                        Thread.sleep(10);
                    }
                    for(int i = 250 ; i >-100; i--)
                    {
                        moveleft();
                        Thread.sleep(10);
                    }
                }
                else {
                    semaphore.acquire();
                    if (dir.equals("up")) {
                        for (int i = 429; i > -50; i--) {
                            moveup();
                            Thread.sleep(10);
                        }
                    } else if (dir.equals("right")) {
                        for (int i = 429; i >= 280; i--) {
                            moveup();
                            Thread.sleep(10);
                        }
                        for (int i = 150; i < 610; i++) {
                            moveright();
                            Thread.sleep(10);
                        }

                    }
                }
            }else if(location.equals("up"))
            {
                for(int i = 550; i>=460;i--)
                {
                    movedown();
                    Thread.sleep(10);
                }
                if(dir.equals("left"))
                {
                    for(int i = 429; i>=355;i--)
                    {
                        movedown();
                        Thread.sleep(10);
                    }
                    for(int i = 250 ; i >-150; i--)
                    {
                        moveright();
                        Thread.sleep(10);
                    }
                }
                else {
                    semaphore.acquire();
                    if (dir.equals("up")) {
                        for (int i = 429; i > -90; i--) {
                            movedown();
                            Thread.sleep(10);
                        }
                    } else if (dir.equals("right")) {
                        for (int i = 429; i >= 220; i--) {
                            movedown();
                            Thread.sleep(10);
                        }
                        for (int i = 150; i < 610; i++) {
                            moveleft();
                            Thread.sleep(10);
                        }

                    }
                }
            } else if (location.equals("left"))
            {
                for(int i = 600; i>=390;i--)
                {
                    moveright();
                    Thread.sleep(10);
                }
                if(dir.equals("left"))
                {
                    for(int i = 429; i>=355;i--)
                    {
                        moveright();
                        Thread.sleep(10);
                    }
                    for(int i = 250 ; i >-150; i--)
                    {
                        moveup();
                        Thread.sleep(10);
                    }
                }
                else {
                    semaphore.acquire();
                    if (dir.equals("up")) {
                        for (int i = 600; i > 0; i--) {
                            moveright();
                            Thread.sleep(10);
                        }
                    } else if (dir.equals("right")) {
                        for (int i = 429; i >= 220; i--) {
                            moveright();
                            Thread.sleep(10);
                        }
                        for (int i = 150; i < 610; i++) {
                            movedown();
                            Thread.sleep(10);
                        }

                    }
                }
            } else if (location.equals("right")) {
                for (int i = 600; i >= 460; i--) {
                    moveleft();
                    Thread.sleep(10);
                }
                if (dir.equals("left")) {
                    for (int i = 429; i >= 360; i--) {
                        moveleft();
                        Thread.sleep(10);
                    }
                    for (int i = 250; i > -150; i--) {
                        movedown();
                        Thread.sleep(10);
                    }
                } else {
                    semaphore.acquire();
                    if (dir.equals("up")) {
                        for (int i = 640; i > 0; i--) {
                            moveleft();
                            Thread.sleep(10);
                        }
                    } else if (dir.equals("right")) {
                        for (int i = 429; i >= 240; i--) {
                            moveleft();
                            Thread.sleep(10);
                        }
                        for (int i = 150; i < 610; i++) {
                            moveup();
                            Thread.sleep(10);
                        }
                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(dir.equals("right")||dir.equals("up"))
                    semaphore.release();
            System.out.printf("Car %d leaves the intersection%n", id);
        }
    }


    private  void moveup(){
        angle="straight";
        y-=1;
    }
    private  void movedown() {
        angle="down";
        y+=1;

    }

    private   void moveleft()
    {
        angle="left";
        x -= 1;

    }

    private  void moveright() {
        angle="right";
        x += 1;

    }


    // Draw the car on a given graphics context
    public void draw(GraphicsContext gc) {
        if(angle.equals("straight"))
        {
            image = new Image("C:/Users/thiru/OneDrive/desktop/os_pack/final/cars.png");
            width=20;
            height=40;
        } else if (angle.equals("left")) {
            image = new Image("C:/Users/thiru/OneDrive/desktop/os_pack/final/carl.png");
            width=40;
            height=20;
        } else if (angle.equals("right")) {
            image = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/final/carr.png");
            width=40;
            height=20;
        } else if (angle.equals("down")) {
            image = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/final/card.png");
            width=20;
            height=40;
        }
        gc.drawImage(image,x,y, width,height);

    }

}

public class CarSimulation extends Application {

    private List<Car> cars = new ArrayList<>();
    private String dir;
    private String location;

    private   int i=1;
    private  int width =50;
    @Override
    public void start(Stage primaryStage) {


        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        dir="up";
                        break;
                    case DOWN:
                        dir="down";
                        break;
                    case LEFT:
                        dir="left";
                        break;
                    case RIGHT:
                        dir="right";
                        break;
                    case D:
                        location="down";
                        break;
                    case L:
                        location="left";
                        break;
                    case R:
                        location="right";
                        break;
                    case U:
                        location="up";
                        break;
                    case SPACE:
                        create();
                        break;

                }
            }
        });


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
            // Clear
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            draw1(gc);

            for (Car car : cars) {
                car.draw(gc);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();




        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void create()
    {
        int xcor=280;
        int ycor=550;
        if(location.equals("down"))
        {
            if(dir.equals("left"))
                xcor=280;
            else if (dir.equals("up"))
                xcor=310;
            else if(dir.equals("right"))
                xcor =340;
        }
        else if(location.equals("left"))
        {
            xcor=0;
            if(dir.equals("left"))
                ycor=215;
            else if (dir.equals("up"))
                ycor=245;
            else if(dir.equals("right"))
                ycor =275;
        }
        else if(location.equals("right"))
        {
            xcor=700;
            if(dir.equals("left"))
                ycor=375;
            else if (dir.equals("up"))
                ycor=345;
            else if(dir.equals("right"))
                ycor =315;
        }
        else if(location.equals("up"))
        {
            ycor=50;
            if(dir.equals("right"))
                xcor=420;
            else if (dir.equals("up"))
                xcor=450;
            else if(dir.equals("left"))
                xcor =490;
        }

        Car car = new Car(xcor, ycor, dir,i,location);
        cars.add(car);
        new Thread(car).start();
        i++;

    }
    private void draw1(GraphicsContext gc) {
        Image road= new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/final/road.jpg");
        gc.drawImage(road,0,0,800,600);
    }

    public static void main(String[] args) {
        launch(args);
    }
}