package graphvis.group30;

import java.util.Timer;
import java.util.TimerTask;

public class CountTimer{

    public int Seconds;
    public int Minutes;
    public boolean isPaused = true;
    public boolean countDown = true;

    public CountTimer(int Minutes, int Seconds, boolean countDown){
        this.Seconds = Seconds;
        this.Minutes = Minutes;
        this.countDown = countDown;
    }

    int delay = 1000;
    int period = 1000;


    Timer countDownTimer = new Timer();
    TimerTask show = new TimerTask() {
        @Override
        public void run(){
            System.out.println("running");
            if(countDown == true){
                if(Seconds == 0 && Minutes == 0){
                    // show a popup of that the game has ended 
                    countDownTimer.cancel();
                } else if(isPaused == false){
                    //Frontend.timerLabel.setText(Minutes + ":" + Seconds);
                    Frontend.testString = Minutes + ":" + Seconds;
                    System.out.println(Frontend.testString);
                    Seconds --;
                    if(Seconds == -1){
                        Seconds = 60;
                        Minutes --;
                    }
                }
            } else {
                if(isPaused == false){
                    //Frontend.timerLabel.setText(Minutes + ":" + Seconds);
                    Frontend.testString = Minutes + ":" + Seconds;
                    System.out.println(Frontend.testString);
                    Seconds ++;
                    if(Seconds == 61){
                        Seconds = 00;
                        Minutes ++;
                    }
                }
            }
        }
    };

    public void runTimer(){
        countDownTimer.scheduleAtFixedRate(show,delay,period);
    }

    public void stop(){
        countDownTimer.cancel();
    }

    public void setPause(boolean yes){
        isPaused = yes;
    }

}