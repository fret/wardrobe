package com.example.dopamine.wardrobe.Models;
import android.content.Context;
import com.example.dopamine.wardrobe.Database.AppDB;
import java.util.Random;

/**
 * Created by Dopamine on 12/31/2015.
 */
public class Shuffle {

    int[] num;
    int top_size;
    int bottom_size;
    int pointer;
    AppDB appDB;

    public Shuffle(Context context){
        appDB=new AppDB(context);
        top_size=appDB.getTopCount();
        bottom_size=appDB.getBottomCount();
        num = new int[top_size*bottom_size];
        for(int i=0; i<num.length; i++){
            num[i]=i;
        }
        ShuffleArray(num);
        pointer=0;
    }

    private void ShuffleArray(int[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

    public Combination getCombination(){
        int a=0, b=0;
        if(pointer == (top_size*bottom_size-1)){
            pointer=0;
        }
        if(num[pointer]%bottom_size==0){
            a=num[pointer]/bottom_size-1;
            b=bottom_size;
        }
        else {
            a=num[pointer]/bottom_size;
            b=num[pointer]%bottom_size-1;
        }
        pointer++;
        return new Combination(a,b);
    }

    public void refresh(){
        top_size=appDB.getTopCount();
        bottom_size=appDB.getBottomCount();
        num = new int[top_size*bottom_size];
        for(int i=0; i<num.length; i++){
            num[i]=i;
        }
        ShuffleArray(num);
        pointer=0;
    }
}