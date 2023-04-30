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



public class CigaretteSmokers extends Application {

    private List<Smoker> smokers = new ArrayList<>();
    private List<String> ingredients = new ArrayList<>();
    static Semaphore tobacco = new Semaphore(0);
    static Semaphore paper = new Semaphore(0);
    static Semaphore matches = new Semaphore(0);
    static Semaphore dealer = new Semaphore(1);
    String ingredient;

    private int i = 0;

    @Override
    public void start(Stage primaryStage) {

        // Initialize the JavaFX canvas and graphics context
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        Dealer dealer1 = new Dealer();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case T:
                        ingredient="tobacco";
                        i++;
                        Smoker smokert = new Smoker(ingredient,i,340,0,300,0,paper,matches);
                        smokers.add(smokert);
                        ingredients.add(ingredient);
                        new Thread(smokert).start();
                        break;
                    case P:
                        ingredient="paper";
                        i++;
                        Smoker smokerp = new Smoker(ingredient,i,780,345,740,345,tobacco,matches);
                        smokers.add(smokerp);
                        new Thread(smokerp).start();
                        ingredients.add(ingredient);
                        break;
                    case M:
                        ingredient="matches";
                        i++;
                        Smoker smokerm = new Smoker(ingredient,i,340,550,300,550,tobacco,paper);
                        smokers.add(smokerm);
                        new Thread(smokerm).start();
                        ingredients.add(ingredient);
                        break;

                }
                if(i%3==0)
                {
                    System.out.println(i);
                    new Thread(dealer1).start();
                }
            }
        });


        // Initialize the JavaFX Timeline to animate the cars
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
            // Clear
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            draw1(gc);

            // Update and draw each car
            for (Smoker  smoker : smokers) {
                smoker.draw(gc);
            }
            dealer1.draw2(gc);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


        // Set up the JavaFX scene and show the stage

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void draw1(GraphicsContext gc) {
        Image road= new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/road.jpg");
        gc.drawImage(road,0,0,800,600);
    }

    public static void main(String[] args) {
        launch(args);
    }

    class Smoker implements Runnable {
        private double x;
        private double y;
        private double xp;
        private double yp;
        private double width1;
        private double height1;
        private double width2;
        private double height2;
        private double width3=40;
        private double height3=180;
        private String ingredient ;
        private Image  image1;
        private Image image2;
        private Image image3;
        private double xc=-100;
        private double yc=-100;

        Semaphore semaphore1;
        Semaphore semaphore2;

        private final int id;

        public Smoker(String ingredient,int id,double x, double y,double xp,double yp,Semaphore s1,Semaphore s2) {
            this.x = x;
            this.y = y;
            this.xp=xp;
            this.yp=yp;
            this.id=id;
            this.ingredient=ingredient;
            this.semaphore1=s1;
            this.semaphore2=s2;

        }



        @Override
        public void run() {
            try {
                System.out.printf("Smoker %d with %s is waiting for ingredient\n", id,ingredient);

                System.out.printf("Smoker %d enters the intersection%n", id);


                if(ingredient.equals("tobacco"))
                {
                    for(int i = 550; i>=430;i--)
                    {
                        movedown();
                        movedown_yp();
                        Thread.sleep(10);
                    }
                    semaphore1.acquire();
                    System.out.println("Smoker with tobacco accepts paper");
                    semaphore2.acquire();
                    System.out.println("Smoker with tobacco accepts matches");
                    for(int i = 0; i<=100;i++) {
                        movedown();
                        movedown_yp();
                        Thread.sleep(10);
                    }
                    for(int i = 0; i<=140;i++) {
                        moveleft();
                        Thread.sleep(10);
                    }
                    Thread.sleep(1000);
                    x=-50;
                    y=-50;
                    xc=200;
                    yc=220;
                    for(i=0;i<=180;i++)
                    {
                        height3--;
                        Thread.sleep(30);
                    }
                    Thread.sleep(2000);
                    xc=-100;
                    yc=-100;
                    for(i=0;i<=160;i++)
                    {
                        moveright_xp();
                        Thread.sleep(10);

                    }
                    for(i=0;i<=400;i++)
                    {
                        moveup_yp();
                        Thread.sleep(10);
                    }
                    Thread.sleep(2000);
                }
                else if(ingredient.equals("paper"))
                {
                    for (int i = 600; i >= 420; i--) {
                        moveleft();
                        moveleft_xp();
                        Thread.sleep(10);
                    }
                    semaphore1.acquire();
                    System.out.println("Smoker with paper accepts tobacco");
                    semaphore2.acquire();
                    System.out.println("Smoker with paper accepts matches");
                    for(int i = 0; i<=255;i++)
                    {
                        moveleft();
                        moveleft_xp();
                        Thread.sleep(10);
                    }
                    for(int i = 0; i<=140;i++)
                    {
                        moveleft();
                        Thread.sleep(10);
                    }
                    Thread.sleep(1000);
                    x=-50;
                    y=-50;
                    xc=200;
                    yc=220;
                    for(i=0;i<=180;i++)
                    {
                        height3--;
                        Thread.sleep(30);
                    }
                    Thread.sleep(2000);
                    xc=-100;
                    yc=-100;
                    for(i=0;i<=100;i++)
                    {
                        moveup_yp();
                        Thread.sleep(10);

                    }
                    for(i=0;i<=800;i++)
                    {
                        moveright_xp();
                        Thread.sleep(10);
                    }
                    Thread.sleep(2000);
                }
                else if(ingredient.equals("matches"))
                {
                    for(int i = 550; i>=420;i--)
                    {
                        moveup();
                        moveup_yp();
                        Thread.sleep(10);
                    }
                    semaphore1.acquire();
                    System.out.println("Smoker with matches accepts paper");
                    semaphore2.acquire();
                    System.out.println("Smoker with matches accepts tobacco");
                    for(int i = 0; i<=130;i++)
                    {
                        moveup();
                        moveup_yp();
                        Thread.sleep(10);
                    }
                    for (int i = 0; i <=140; i++) {
                        moveleft();
                        Thread.sleep(10);
                    }
                    Thread.sleep(1000);
                    x=-50;
                    y=-50;
                    xc=200;
                    yc=220;
                    for(i=0;i<=180;i++)
                    {
                        height3--;
                        Thread.sleep(30);
                    }
                    Thread.sleep(2000);

                    xc=-100;
                    yc=-100;
                    for(i=0;i<=150;i++)
                    {
                        moveright_xp();
                        Thread.sleep(10);

                    }
                    for(i=0;i<=400;i++)
                    {
                        movedown_yp();
                        Thread.sleep(10);
                    }
                    Thread.sleep(2000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                dealer.release();
                System.out.printf("Smoker %d leaves the intersection%n", id);

            }
        }


        private  void moveup(){
            y-=1;
        }
        private  void movedown() {
            y+=1;

        }
        private  void moveup_yp(){
            yp-=1;
        }
        private  void movedown_yp() {
            yp+=1;

        }
        private   void moveleft()
        {
            x -= 1;

        }

        private   void moveleft_xp()
        {
            xp -= 1;

        }


        private   void moveright_xp()
        {
            xp += 1;

        }



        public void draw(GraphicsContext gc) {
            if(ingredient.equals("tobacco"))
            {
                image1 = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/final/man.png");
                image2 = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/tobacco.JPG");

            } else if (ingredient.equals("paper")) {
                image1 = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/final/man.png");
                image2 = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/paper.JPG");
            } else if (ingredient.equals("matches")) {
                image1 = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/final/man.png");
                image2 = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/match.png");
            }
            image3=new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/final/cige.png");
            width1=20;
            height1=40;
            width2=40;
            height2=40;
            gc.drawImage(image1,xp,yp, width1,height1);
            gc.drawImage(image2,x,y, width2,height2);
            gc.drawImage(image3,xc,yc,width3,height3);

        }


    }
    class Dealer implements Runnable {
        private Image  image1;
        private Image image2;
        private Image image3;
        private Image image4;
        private double xd=20;
        private double yd=250;
        private double xp=60;
        private double yp=360;
        private double xm=60;
        private double ym=285;
        private double xt=60;
        private double yt=220;
        private double width1;
        private double height1;
        private double width2;
        private double height2;

        public void run() {
            for(String ingredient_s :ingredients) {
                System.out.println(ingredient_s);
                try {
                    // Wait for the smoker to finish smoking
                    dealer.acquire();

                    // Choose a random ingredient to provide

                    // Provide the missing ingredient

                    switch (ingredient_s) {
                        case "matches":
                            System.out.println("Dealer provides tobacco and paper.");
                            tobacco.release();
                            paper.release();
                            for(i=0;i<=130;i++)
                            {
                                moveright_xt();
                                moveright_xp();
                                Thread.sleep(10);
                            }
                            Thread.sleep(2000);
                            xp=-100;
                            yp=-100;
                            xt=-100;
                            yt=-100;
                            Thread.sleep(9000);
                            break;
                        case "paper":
                            System.out.println("Dealer provides tobacco and matches.");
                            tobacco.release();
                            matches.release();
                            for(i=0;i<=130;i++)
                            {
                                moveright_xt();
                                moveright_xm();
                                Thread.sleep(10);
                            }
                            Thread.sleep(2000);
                            xm=-100;
                            ym=-100;
                            xt=-100;
                            yt=-100;
                            Thread.sleep(12000);
                            break;
                        case "tobacco":
                            System.out.println("Dealer provides paper and matches.");
                            paper.release();
                            matches.release();
                            for(i=0;i<=130;i++)
                            {
                                moveright_xm();
                                moveright_xp();
                                Thread.sleep(10);
                            }
                            Thread.sleep(2000);
                            xp=-100;
                            yp=-100;
                            xm=-100;
                            ym=-100;
                            Thread.sleep(8000);
                            break;
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    xp=60;
                    yp=360;
                    xm=60;
                    ym=285;
                    xt=60;
                    yt=220;
                }
            }
            ingredients.clear();
        }


        private  void moveright_xp() {
            xp += 1;

        }

        private  void moveright_xm() {
            xm += 1;

        }

        private  void moveright_xt() {
            xt += 1;

        }

        void draw2(GraphicsContext gc) {
            image1 = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/final/man.png");
            image2 = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/tobacco.JPG");
            image3 = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/paper.JPG");
            image4 = new Image("C:/Users/thiru/OneDrive/Desktop/os_pack/match.PNG");
            width1=20;
            height1=40;
            width2=40;
            height2=40;
            gc.drawImage(image1,xd,yd, width1,height1);
            gc.drawImage(image2,xt,yt, width2,height2);
            gc.drawImage(image3,xp,yp, width2,height2);
            gc.drawImage(image4,xm,ym, width2,height2);

        }
    }

}