package graphvis.group30;

import java.io.IOException;
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
                    popUp ended = new popUp();
                    ended.display("you went over the time limit, you will be returned to the starting screen in 5 seconds");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    countDownTimer.cancel();
                    try {
                        Frontend.setRoot("mainmenu");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if(isPaused == false){
                    //Frontend.timerLabel.setText(Minutes + ":" + Seconds);
                    Seconds --;
                    if(Seconds == -1){
                        Seconds = 60;
                        Minutes --;
                    }
                }
            } else {
                if(isPaused == false){
                    //Frontend.timerLabel.setText(Minutes + ":" + Seconds);
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

    public int getMinutes(){
        return Minutes;
    }

    public int getSeconds(){
        return Seconds;
    }

    public void stop(){
        countDownTimer.cancel();
    }

    public void setPause(boolean yes){
        isPaused = yes;
    }

}