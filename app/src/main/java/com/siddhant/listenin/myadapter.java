package com.siddhant.listenin;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.ViewHolder> {
    private ArrayList<String> listitems;
    MediaPlayer mPlayer = new MediaPlayer();
    int flag=0;
    public myadapter(ArrayList<String> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }


    void play( String audiourl )
    {

        //  Initialize a new media player instance
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
               try{

            mPlayer.setDataSource(audiourl);


            mPlayer.prepare();

            mPlayer.start();



        }catch (IOException e){

            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (SecurityException e){
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }

    }

    private Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu,viewGroup,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
//        final String listitem=listitems.get(i);
        viewHolder.textviewhead.setText(Client.arrayList.get(i));
        //viewHolder.textviewdesc.setText(listitem.getDesc());
        viewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AndroidBuildingMusicPlayerActivity.class);
                intent.putExtra("message", Client.urllist.get(i));
                context.startActivity(intent);

                // Set the media player audio stream type

            }
        });
    }

    @Override
    public int getItemCount() {
        return Client.arrayList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        public TextView textviewhead;
        public LinearLayout ll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // textviewdesc =(TextView)itemView.findViewById(R.id.textviewdesc);
            textviewhead =(TextView)itemView.findViewById(R.id.textviewhead);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
        }
    }
}
