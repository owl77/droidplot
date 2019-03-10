package com.example.owl.functionplotter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.WHITE;




public class MainActivity extends AppCompatActivity {


    class Parser {

// implements Double calculate(String expression, Double value)


        public  String purge(String s){
            String aux = s.replace("  "," ");
            if(aux.equals(s)){return s;}else{return purge(aux);}
        }

        public  String clean(String s){
            if(s.length()> 1){
                if(s.charAt(0)==' '){return clean(s.substring(1,s.length() ));}
                if(s.charAt(s.length()-1)==' '){return clean(s.substring(0,s.length()-1 ));}}
//if(s.charAt(0)=='(' && s.charAt(s.length()-1)==')'){ return clean(s.substring(1,s.length()-1 )); }

            return s;}

        public  String spacer(String s){
            for(int i = 0; i< s.length(); i++){
                if(i< s.length()-1 && symb.contains(""+ s.charAt(i)) && s.charAt(i+1)!=' '){return spacer(s.substring(0,i+1) + " " + s.substring(i+1,s.length()));}
                if(i< s.length()-1 && intcheck(s.substring(i,i+1)) && s.charAt(i+1)!=' ' && !(intcheck(s.substring(i+1,i+2))) ){return spacer(s.substring(0,i+1) + " " + s.substring(i+1,s.length()));}



                if(i> 0 && symb.contains(""+ s.charAt(i)) && s.charAt(i-1)!=' '){return spacer(s.substring(0,i) + " " + s.substring(i,s.length()));}
            }
            return s;}

        public  List<String> maker(String s){


            s = spacer(clean(purge(s)));
            return starinsert(Arrays.asList(s.split(" ")));}
        public  String[] prebinstrongoperators = {"/","*"};
        public  List<String> binstrongoperators = Arrays.asList(prebinstrongoperators);
        public  String[] prebinweakoperators = {"+","-"};
        public  List<String> binweakoperators = Arrays.asList(prebinweakoperators);
        public  String[] prebinoperators = {"-","/","^","*","+"};
        public  List<String> binoperators = Arrays.asList(prebinoperators);
        public  String symb = "-/^*+()x";
        public  String in = "lcst";
        public  String[] preunoperators = {"log","sin","cos","tg","cotg","-"};
        public  List<String> unoperators = Arrays.asList(preunoperators);
        public  String[] prefunct = {"log","sin","cos","tg","cotg"};
        public  List<String> funct = Arrays.asList(prefunct);
        public  Boolean intcheck(String s){try{Integer.parseInt(s); return true;}catch(Exception e){return false;} }
        public  String[] preconstants = {"pi","e"};
        public  List<String> constants = Arrays.asList(preconstants);
        public  List<String> starinsert(List<String> a){
            for(int i = 0; i < a.size()-1; i++)
            {if((a.get(i).equals("x") && intcheck(a.get(i+1))) || (a.get(i+1).equals("x") && intcheck(a.get(i))) ||
                    (a.get(i).equals("x") && a.get(i+1).equals("x") ) || (a.get(i).equals("x") && a.get(i+1).equals("(")  )
                    || (a.get(i).equals(")") && a.get(i+1).equals("x") )
                    || ( intcheck(a.get(i)) && a.get(i+1).equals("(")  )

                    || ( intcheck(a.get(i)) && a.get(i+1).equals("pi")  )

                    || (a.get(i).equals("x") && a.get(i+1).equals("pi")  )


                    || ( intcheck(a.get(i+1)) && a.get(i).equals("pi")  )

                    || (a.get(i+1).equals("x") && a.get(i).equals("pi")  )





                    || (a.get(i).equals("x") && funct.contains(a.get(i+1) )  )
                    || ( intcheck(a.get(i)) && funct.contains(a.get(i+1) )     )

                    || (a.get(i).equals(")") && a.get(i+1).equals("(") )

                    ){List<String> aux = new ArrayList<String>();
                aux.addAll(a);
                aux.add(i+1,"*"); return starinsert(aux); }}
            return a;}

        public Boolean parser(List<String> a){
            if(a.size()== 1 && (a.get(0).equals("x") || constants.contains(a.get(0)) || intcheck(String.valueOf(a.get(0) ) )   )  )
            {return true;}
            if(a.size() > 2 && a.get(0).equals("(") && a.get(a.size()-1).equals(")") && parser(a.subList(1,a.size()-1))    )

            {  return true;}
            for(int i = 1; i< a.size(); i++)
            {if(binoperators.contains(a.get(i))  && parser(a.subList(0,i))  && parser(a.subList(i+1,a.size() ) )  ) {return true;}
            }

            if(a.size() > 1 && unoperators.contains(a.get(0))  && parser(a.subList(1, a.size()))) {return true;}

            return false;}

        public  Boolean parenparser(List<String> a){
            if(a.size() > 2 && a.get(0).equals("(") && a.get(a.size()-1).equals(")") && parser(a.subList(1,a.size()-1))    )
            {  return true;}
            return false;}

        public  List<List<String>> components(List<String> a){
            if(a.size() > 2){if(parser(a.subList(1,a.size() -1) )  ) {return components(a.subList(1,a.size() - 1) ); }  }
            List<List<String>> c = new ArrayList<List<String>>();
            for(int i = 1; i< a.size(); i++)
            {if( parser(a.subList(0,i))  && binweakoperators.contains(a.get(i)) && parser(a.subList(i+1,a.size())))
            {c.add(a.subList(i,i+1));c.add(a.subList(0,i)); c.add(a.subList(i+1,a.size()));
                return c;}}

            for(int i = 1; i< a.size(); i++)
            {

                if( parser(a.subList(0,i))   && binstrongoperators.contains(a.get(i)) && parser(a.subList(i+1,a.size())))
                {c.add(a.subList(i,i+1));c.add(a.subList(0,i)); c.add(a.subList(i+1,a.size()));return c;}


            }



            for(int i = 1; i< a.size(); i++)
            {

                if( parser(a.subList(0,i))   && a.get(i).equals("^") && parser(a.subList(i+1,a.size())))
                {c.add(a.subList(i,i+1));c.add(a.subList(0,i)); c.add(a.subList(i+1,a.size()));return c;}


            }



            if(a.size() > 1 && unoperators.contains(a.get(0))  && parser(a.subList(1, a.size()))) {
                c.add(a.subList(0,1));c.add(a.subList(1,a.size() ) );return c;}

            return c;}
        public List<List<String>> protoparser(String s){

            s = spacer(clean(purge(s)));
            List<String> st = starinsert(Arrays.asList(s.split(" ")));
            return components(st);
        }

        public  String stringparser(String[] s){return String.valueOf(parser(Arrays.asList(s)));}


        public  Double eval(List<String> s, Double val){
            if(s.size()==1){if (s.get(0).equals("pi")){return Math.PI;}

                if(s.get(0).equals("e")){return Math.exp(1);}

                if(intcheck(s.get(0))){Double ret = new Double(s.get(0)); return ret; }

                if(s.get(0).equals("x")){return val;}

            }

            List<List<String>> aux = components(s);

            if(aux.size()>2){
                if(aux.get(0).get(0).equals("*")){return eval(aux.get(1), val) * eval(aux.get(2), val); }
                if(aux.get(0).get(0).equals("+")){return eval(aux.get(1), val) + eval(aux.get(2), val); }
                if(aux.get(0).get(0).equals("-")){return eval(aux.get(1), val) - eval(aux.get(2), val); }
                if(aux.get(0).get(0).equals("/") && eval(aux.get(2), val)!=0 ){return eval(aux.get(1), val) / eval(aux.get(2), val); }
                if(aux.get(0).get(0).equals("^")){return Math.pow(eval(aux.get(1), val), eval(aux.get(2), val)); }
            }

            if(aux.size()== 2){
                if(aux.get(0).get(0).equals("cos")){return Math.cos(eval(aux.get(1), val)); }
                if(aux.get(0).get(0).equals("sin")){return Math.sin(eval(aux.get(1), val)); }
                if(aux.get(0).get(0).equals("tg")){return Math.tan(eval(aux.get(1), val)); }
                if(aux.get(0).get(0).equals("cotg")){return Math.atan(eval(aux.get(1), val));}
                if(aux.get(0).get(0).equals("log")){return Math.log(eval(aux.get(1), val)); }
                if(aux.get(0).get(0).equals("-")){return - eval(aux.get(1), val); }
            }




            int x = 0;
            Double ret = new Double(x);
            return ret;


        }

        public  Double calculate(String s, Double x){return  eval(maker(s), x);}




        }





 public class Graph extends View {

     private Paint paint;
     public Double xlow, xhigh, ylow, yhigh;
     public String formula;

     public Graph(Context context) {
         super(context);
         paint = new Paint();
         paint.setColor(BLACK);
     }


     public void setpar(Double xl, Double xh, Double yl, Double yh, String s) {

         xlow = xl;
         xhigh = xh;
         ylow = yl;
         yhigh = yh;
         formula = s;
     }

     public Double scaler(Double xl, Double xh, Double yl, Double yh, Double i, String s, Parser p){
         return 500 - (yl + yh  * (1000.0/(xh-xl)) * p.calculate(s,xl +((xh -xl)/1000.0)* i ));

     }

     @Override
     protected void onDraw(Canvas canvas) {

         canvas.drawColor(WHITE);
         Parser pars = new Parser();


if(xlow < 0){
    for (Double i = 0.0; i < 1000; i++) {
        canvas.drawPoint((-xlow.floatValue() *(1000/(xhigh.floatValue()-xlow.floatValue()))), i.floatValue()  , paint);
        // canvas.drawCircle(200,200, 100, paint)
        } }

         for (Double i = 0.0; i < 1000; i++) {
             canvas.drawPoint(i.floatValue(), scaler(xlow,xhigh,ylow,yhigh,i,"0", pars).floatValue() , paint);
             // canvas.drawCircle(200,200, 100, paint);
         }
         for (Double i = 0.0; i < 1000; i++) {
             canvas.drawPoint(i.floatValue(), 1000 , paint);
             // canvas.drawCircle(200,200, 100, paint);
         }



         for(Double i = 0.0; i < 1000; i++) {
             canvas.drawCircle(i.floatValue(), scaler(xlow,xhigh,ylow,yhigh,i,formula, pars).floatValue() , 1, paint);
             // canvas.drawCircle(200,200, 100, paint);
         }
     }
 }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        String savedfun = null;

        if (getIntent().hasExtra("saved")) {
            savedfun = getIntent().getStringExtra("saved");
        }


        String savedxlow = null;

        if (getIntent().hasExtra("xlow")) {
            savedxlow = getIntent().getStringExtra("xlow");
        }

        String savedxhigh = null;

        if (getIntent().hasExtra("xhigh")) {
            savedxhigh = getIntent().getStringExtra("xhigh");
        }






        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));


        // layout.addView(new Graph(this));
        final EditText funct = new EditText(this);
        funct.setHint("function expression f(x)");
        if(savedfun!=null){funct.setText(savedfun);}
         layout.addView(funct);

        final EditText xlow = new EditText(this);
        xlow.setHint("lowest x value");
        if(savedxlow!=null){xlow.setText(savedxlow);}
        layout.addView(xlow);

        final EditText xhigh = new EditText(this);
        xhigh.setHint("highest x value");
        if(savedxhigh!=null){xhigh.setText(savedxhigh);}
        layout.addView(xhigh);

        final EditText ylow = new EditText(this);
        ylow.setText("0");
        layout.addView(ylow);

        final EditText yhigh = new EditText(this);
        yhigh.setHint("relative y-scaling (1 = same as x-axis)");
        layout.addView(yhigh);

        Button drawbutton = new Button(this);
        drawbutton.setText("Draw Graph");
        layout.addView(drawbutton);

        setContentView(layout);

        drawbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Code here executes on main thread after user presses button



                final String Func = funct.getText().toString();

                final String xLow = xlow.getText().toString(), xHigh = xhigh.getText().toString(), yLow = ylow.getText().toString(),
                        yHigh = yhigh.getText().toString();


                Graph graph = new Graph(getApplicationContext());

                graph.setpar(Double.valueOf(xLow) ,Double.valueOf(xHigh), Double.valueOf(yLow), Double.valueOf(yHigh), Func);

                LinearLayout layout2 = new LinearLayout(getApplicationContext());
                layout2.setOrientation(LinearLayout.VERTICAL);
                layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

                // layout.addView(new Graph(this));

                Button ret = new Button(getApplicationContext());
                ret.setText("Back to Menu");


                ret.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        Intent back = new Intent(getApplicationContext(), MainActivity.class);
                        back.putExtra("saved", Func);
                        back.putExtra("xlow", xLow);
                        back.putExtra("xhigh", xHigh);
                        back.putExtra("ylow", yHigh);
                        back.putExtra("yhigh", yHigh);





                        startActivity(back);
                    }


                });






                layout2.addView(ret);
                layout2.addView(graph);
               setContentView(layout2);
            }
        });






    }
}
