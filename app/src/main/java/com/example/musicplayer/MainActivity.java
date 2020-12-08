package com.example.musicplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView playing;
    private int[] flag = {0};
    private int[] sf = {0};
    private int[] musics = {R.raw.flashdance,R.raw.hemisphere,
            R.raw.lily,R.raw.nible,R.raw.offshore,
            R.raw.prime,R.raw.trajectory,R.raw.stardust};
    private int[] index = {1,2,3,4,5,6,7,8};
    private String[] id = {"1","2","3","4","5","6","7","8"};
    private String[] songs = {"flashdance","hemisphere","lily","nible",
            "offshore","Prime","trajectory","stardust"};
    private String[] maker = {"Litmus*","ARForest","Jun Kuroda","SC-KIM",
            "Nhato","Pokku'n","LegoLee","mmry"};
    private int[] num = {0,0,0,0,0,0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.song_list);
        ListView listv =findViewById(R.id.musiclist);
        playing = findViewById(R.id.song_currentplay);
        registerForContextMenu(listView);
        mediaPlayer=MediaPlayer.create(this,R.raw.flashdance);
        setListview(getAll());
        playing.setText("Now Playing: ");
        sf[0]=1;
        seekBar = findViewById(R.id.play_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }


            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBar.setMax(mediaPlayer.getDuration());
        new myThread().start();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView songid = view.findViewById(R.id.song_id);
                String strid = songid.getText().toString();
                int a = Integer.parseInt(strid);
                if(a == 2)
                {
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,musics[1]);
                    playing.setText("Now Playing: hemisphere");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    sf[0]=2;
                }else if(a == 3)
                {
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,musics[2]);
                    playing.setText("Now Playing: lily");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    sf[0]=3;
                }else if(a == 4)
                {
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,musics[3]);
                    playing.setText("Now Playing: nible");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    sf[0]=4;
                }else if(a == 5)
                {
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,musics[4]);
                    playing.setText("Now Playing: offshore");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    sf[0]=5;
                }
                else if(a == 6)
                {
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,musics[5]);
                    playing.setText("Now Playing: prime");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    sf[0]=6;
                }
                else if(a == 7)
                {
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,musics[6]);
                    playing.setText("Now Playing: trajectory");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    sf[0]=7;
                }else if(a == 8)
                {
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,musics[7]);
                    playing.setText("Now Playing: Stardust");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    sf[0]=8;
                }else
                {
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,musics[0]);
                    playing.setText("Now Playing: flashdance");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    sf[0]=1;
                }
            }
        });
        Button last = findViewById(R.id.play_last);
        Button play = findViewById(R.id.play_stop);
        Button next = findViewById(R.id.play_next);
        Spinner spinner = findViewById(R.id.play_mode);
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.items,R.layout.support_simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);;
        spinner.setAdapter(adapter1);
        spinner.setSelection(flag[0]);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String choice = (String)adapter1.getItem(i);
                switch (choice)
                {
                    case "Order":
                        autoplay();
                        break;
                    case "Random":
                        break;
                    case "Single":
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button btn=(Button)findViewById(R.id.song_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit=(EditText)findViewById(R.id.song_edit);

                if(edit.getText() == null){

                }else{
                    String s=edit.getText().toString();
                    int b=Integer.parseInt(s);
                    index[b-1]=b;
                    setListview(getAll());
                    num[b-1]=0;
                    TextView v1=(TextView)findViewById(R.id.blank);
                    String str="";
                    for(int j=0;j<num.length;j++){
                        if(num[j]==1){
                            str=str+"   "+"1.flashdance";
                        }else if(num[j]==2){
                            str=str+"   "+"2.hemisphere";
                        }else if(num[j]==3){
                            str=str+"   "+"3.lily";
                        }else if(num[j]==4){
                            str=str+"   "+"4.nible";
                        }else if(num[j]==5){
                            str=str+"   "+"5.offshore";
                        }else if(num[j]==6){
                            str=str+"   "+"6.prime";
                        }else if(num[j]==7){
                            str=str+"   "+"7.trajectory";
                        }else if(num[j]==8){
                            str=str+"   "+"8.stardust";
                        }
                    }
                    v1.setText(str);
                }

            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sf[0]>1){
                    for(int i=sf[0]-2;i>=0;i--){
                        if(index[i]>0){
                            sf[0]=i+1;
                            mediaPlayer.reset();
                            mediaPlayer=MediaPlayer.create(MainActivity.this,musics[i]);
                            playing.setText("Now Playing："+songs[i]);
                            mediaPlayer.start();
                            seekBar.setMax(mediaPlayer.getDuration());
                            break;
                        }
                    }
                }else {
                    sf[0]=8;
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,musics[7]);
                    playing.setText("Now Playing: stardust");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null&&!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }else{
                    mediaPlayer.pause();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sf[0]<8){
                    for(int i=sf[0];i<8;i++){
                        if(index[i]>0){
                            sf[0]=i+1;
                            mediaPlayer.reset();
                            mediaPlayer=MediaPlayer.create(MainActivity.this,musics[i]);
                            playing.setText("Now Playing:"+songs[i]);
                            mediaPlayer.start();
                            seekBar.setMax(mediaPlayer.getDuration());
                            break;
                        }
                    }
                }else {
                    sf[0]=1;
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.flashdance);
                    playing.setText("Now Playing: Flashdance");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                }
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menuoption,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View viewDialog = LayoutInflater.from(this).inflate(R.layout.helpdialog,null,false);
        builder.setTitle("Help")
                .setView(viewDialog)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
        return super.onOptionsItemSelected(item);
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contextoption, menu);
    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=null;
        View itemView=null;
        TextView textId=null;
        TextView text=null;
        switch (item.getItemId()){
            //删除
            case R.id.music_delete:
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                itemView=info.targetView;
                textId =(TextView)itemView.findViewById(R.id.song_id);
                text=(TextView)itemView.findViewById(R.id.song_name);
                final String strid= textId.getText().toString();
                final String t= text.getText().toString();
                final int a= Integer.parseInt(strid);
                new AlertDialog.Builder(this)
                        .setTitle("Delete Song").setMessage("Are You Sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for(int j=0;j<index.length;j++){
                                    if(index[j]==a){
                                        index[j]=0;
                                    }
                                }
                                setListview(getAll());
                                num[a-1]=a;
                                TextView v=(TextView)findViewById(R.id.blank);
                                String str="";
                                for(int j=0;j<num.length;j++){
                                    if(num[j]==1){
                                        str=str+"   "+"1.flashdance";
                                    }else if(num[j]==2){
                                        str=str+"   "+"2.hemisphere";
                                    }else if(num[j]==3){
                                        str=str+"   "+"3.lily";
                                    }else if(num[j]==4){
                                        str=str+"   "+"4.nible";
                                    }else if(num[j]==5){
                                        str=str+"   "+"5.offshore";
                                    }else if(num[j]==6){
                                        str=str+"   "+"6.prime";
                                    }else if(num[j]==7){
                                        str=str+"   "+"7.trajectory";
                                    }else if(num[j]==8){
                                        str=str+"   "+"8.stardust";
                                    }
                                }
                                v.setText(str);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();

                break;

        }
        return true;
    }
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }
    public ArrayList<Map<String,Object>> getAll()
    {
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<index.length;i++) {
            if(index[i]>0){
                Map<String,Object> item=new HashMap<String,Object>();
                item.put("id", id[i]);
                item.put("songs", songs[i]);
                item.put("maker",maker[i]);
                list.add(item);
            }
        }
        return list;
    }
    public void autoplay(){
        if(sf[0]<8){
            for(int i=sf[0];i<8;i++){
                if(index[i]>0){
                    sf[0]=i+1;
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(MainActivity.this,musics[i]);
                    playing.setText("Now Playing: "+songs[i]);
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    break;
                }
            }
        }else {
            sf[0]=1;
            mediaPlayer.reset();
            mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.flashdance);
            playing.setText("Now Playing: flashdance");
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
        }

    }
    private void setListview(ArrayList<Map<String, Object>> items) {
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.song_item,
                new String[]{"id", "songs", "maker"},
                new int[]{R.id.song_id, R.id.song_name, R.id.song_maker});
        listView.setAdapter(adapter);
    }
    class myThread extends Thread{
        @Override
        public void run()
        {
            super.run();
            while(seekBar.getProgress()<=seekBar.getMax())
            {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }
    }
}