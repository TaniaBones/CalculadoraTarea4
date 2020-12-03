package com.example.calculadoratarea4;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity {

    //definimos todos los elementos que vamos a utilizar
    protected TextView pantalla;
    protected Button uno,dos,tres,cuatro,cinco,seis,siete,ocho,nueve,cero,suma,resta,prod,div,borrar,igual;
    protected String input,respuesta;
    protected ConstraintLayout activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //relacionamos los elementos con su equivalente en los xml
        //pantalla va a ser el textview donde se muestren los números y las operaciones
        pantalla=findViewById(R.id.pantalla);

        cero = (Button) findViewById(R.id.button);
        uno = (Button) findViewById(R.id.button1);
        dos = (Button) findViewById(R.id.button2);
        tres = (Button)findViewById(R.id.button3);
        cuatro = (Button) findViewById(R.id.button4);
        cinco = (Button) findViewById(R.id.button5);
        seis = (Button) findViewById(R.id.button6);
        siete = (Button) findViewById(R.id.button7);
        ocho = (Button) findViewById(R.id.button8);
        nueve = (Button) findViewById(R.id.button9);
        suma = (Button) findViewById(R.id.buttonSuma);
        resta = (Button) findViewById(R.id.buttonResta);
        prod = (Button) findViewById(R.id.buttonProducto);
        div = (Button) findViewById(R.id.buttonDiv);
        borrar = (Button) findViewById(R.id.buttonC);
        igual = (Button) findViewById(R.id.buttonIgual);

        //activity va a ser la referencia para poder cambiar el fondo de nuestra aplicación más adelante
        activity = (ConstraintLayout) findViewById(R.id.Capa);

        //registerForContextMenu para poder generar un menú contextual cuando se mantenga pulsada la pantalla
        registerForContextMenu(pantalla);

    }

    public void ButtonClick(View view){
        Button button = (Button) view;
        //creamos un string data que recoja el texto que contiene el mismo botón
        String data = button.getText().toString();

        // creamos un switch que nos analice el contenido de data
        switch (data) {
            //cuando se pulsa la tecla borrar nos devolverá el textview vacío
            case "C":
                input = "";
                break;
            case "x":
                //cuando se pulsa la tecla X nos devolverá el símbolo que se usa para el producto
                resolver();
                input += "*";
                break;
            case "=":
                //cuando se pulsa la tecla = se nos dará la respuesta tras el uso del método resolver
                resolver();
                respuesta = input;
                break;
            default:
                //en cualquier otro caso se busca si el input está vacío, que entonces seguirá vacío
                if(input == null){
                    input = "";
                }
                //en caso de que data registre alguno de los siguientes caracteres, pasará al método resolver
                if(data.equals("+") || data.equals("-") || data.equals("/")){
                    resolver();
                }
                //en la variable input se añadirá el contenido de data, sea cual sea
                input += data;
        }
        //nos mostrará en pantalla la operación escrita sin resolver
        pantalla.setText(input);
    }

    //creamos el método resolver, que registrará el resultado en la variable input
    private void resolver(){
        //si el largo del split del input es igual a 2, quitando y separando los elementos de ese array
        //generado por el split por el símbolo registrado en regex
        if(input.split("\\*").length==2){
            //entra dentro del condicional y se crea un array de texto llamado number, que contiene los
            //dos parametros, ignorando el producto en este caso
            String number[]=input.split("\\*");
            //de la siguiente manera, creamos una variable producto que registre el primer elemento del array
            //multiplicado por el segundo elemento del array
            double prod=Double.parseDouble(number[0])*Double.parseDouble(number[1]);
            //ese producto lo registramos en el input para su posterior muestra en pantalla
            input = prod+"";
        }
        else if(input.split("/").length==2){
            //entra dentro del condicional y se crea un array de texto llamado number, que contiene los
            //dos parametros, ignorando el producto en este caso
            String number[]=input.split("/");
            //de la siguiente manera, creamos una variable div que registre el primer elemento del array
            //dividido entre el segundo elemento del array
            double div=Double.parseDouble(number[0])/Double.parseDouble(number[1]);
            //esa división la registramos en el input para su posterior muestra en pantalla
            input = div+"";
        }
        else if(input.split("\\+").length==2){
            //entra dentro del condicional y se crea un array de texto llamado number, que contiene los
            //dos parametros, ignorando el producto en este caso
            String number[]=input.split("\\+");
            //de la siguiente manera, creamos una variable suma que registre el primer elemento del array
            //sumado al segundo elemento del array
            double suma=Double.parseDouble(number[0])+Double.parseDouble(number[1]);
            //esa suma la registramos en el input para su posterior muestra en pantalla
            input = suma+"";
        }
        else if(input.split("-").length==2){
            //entra dentro del condicional y se crea un array de texto llamado number, que contiene los
            //dos parametros, ignorando el producto en este caso
            String number[]=input.split("-");
            //de la siguiente manera, creamos una variable resta que registre el primer elemento del array
            //menos el segundo elemento del array
            double resta=Double.parseDouble(number[0])-Double.parseDouble(number[1]);
            //esa resta la registramos en el input para su posterior muestra en pantalla
            input = resta+"";
        }

        //cuando el tamaño del array generado por el contenido del textview sea menor de 2 quiere decir que
        //le falta un segundo operador o el operando
        else if (input.split("-").length<2 || input.split("/").length<2 || input.split("\\+").length<2 ||
        input.split("\\*").length<2){
            //será entonces cuando se lance el mensaje en un Toast
            Toast.makeText(MainActivity.this, "Falta un operando",Toast.LENGTH_LONG).show();
        }

        //aquí se nos mostrará por pantalla el resultado final
        pantalla.setText(input);

    }

    //método para cuando se pulsa el menú
    public boolean onCreateOptionsMenu(Menu menu){
        //tenemos que inflar el menú para poder usarlo, que es como darle forma
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    //método para las funciones del menú
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //con un if marcamos las acciones de cada opción seleccionada
         if (id==R.id.iconoCierre){
             //si pulsamos el iconoCierre, se ejecutará el método cerrar() definido más abajo
                cerrar(); }
         if (id==R.id.textoNegro){
             //si pulsamos textoNegro se nos cambiarán todos los textos a negro
             pantalla.setTextColor(Color.GRAY);
             cero.setTextColor(Color.GRAY);
             uno.setTextColor(Color.GRAY);
             dos.setTextColor(Color.GRAY);
             tres.setTextColor(Color.GRAY);
             cuatro.setTextColor(Color.GRAY);
             cinco.setTextColor(Color.GRAY);
             seis.setTextColor(Color.GRAY);
             siete.setTextColor(Color.GRAY);
             ocho.setTextColor(Color.GRAY);
             nueve.setTextColor(Color.GRAY);
             suma.setTextColor(Color.GRAY);
             resta.setTextColor(Color.GRAY);
             prod.setTextColor(Color.GRAY);
             div.setTextColor(Color.GRAY);
             igual.setTextColor(Color.GRAY);
             borrar.setTextColor(Color.GRAY);
         }
         if (id==R.id.textoAzul){
             //si pulsamos textoAzul se nos cambiarán todos los textos a azul
             pantalla.setTextColor(Color.BLUE);
             cero.setTextColor(Color.BLUE);
             uno.setTextColor(Color.BLUE);
             dos.setTextColor(Color.BLUE);
             tres.setTextColor(Color.BLUE);
             cuatro.setTextColor(Color.BLUE);
             cinco.setTextColor(Color.BLUE);
             seis.setTextColor(Color.BLUE);
             siete.setTextColor(Color.BLUE);
             ocho.setTextColor(Color.BLUE);
             nueve.setTextColor(Color.BLUE);
             suma.setTextColor(Color.BLUE);
             resta.setTextColor(Color.BLUE);
             prod.setTextColor(Color.BLUE);
             div.setTextColor(Color.BLUE);
             igual.setTextColor(Color.BLUE);
             borrar.setTextColor(Color.BLUE);
         }
         if (id==R.id.textoRojo){
             //si pulsamod textoRojo se nos cambiarán todos los textos a rojo
             pantalla.setTextColor(RED);
             cero.setTextColor(Color.RED);
             uno.setTextColor(Color.RED);
             dos.setTextColor(Color.RED);
             tres.setTextColor(Color.RED);
             cuatro.setTextColor(Color.RED);
             cinco.setTextColor(Color.RED);
             seis.setTextColor(Color.RED);
             siete.setTextColor(Color.RED);
             ocho.setTextColor(Color.RED);
             nueve.setTextColor(Color.RED);
             suma.setTextColor(Color.RED);
             resta.setTextColor(Color.RED);
             prod.setTextColor(Color.RED);
             div.setTextColor(Color.RED);
             igual.setTextColor(Color.RED);
             borrar.setTextColor(Color.RED);
         }
         if (id==R.id.colorFondo) {
             //en el caso de pulsar el colorFondo nos tiene que salir un cuadro de dialogo y para ello debemos crear
            //un AlertDialog, y lo creamos en ActivityMain, y los objetos que le adjudicamos son los elementos contenidos
             //en el array fondos en el strings.xml
             new AlertDialog.Builder(this).setItems(R.array.fondos, new DialogInterface.OnClickListener() {
                 @Override
                 //dentro del cuadro de diálogo se debe generar un método onClick para cuando se pulse alguno de sus elementos
                 public void onClick(DialogInterface dialog, int which) {
                     //para eso definimos un switch, que como le habíamos dado un array anteriormente, debemos
                     //decirle que en función de si se elige el primer elemento o el segundo, se le de al fondo un color u otro
                     switch(which){
                         case 0:
                             activity.setBackgroundColor(Color.WHITE);
                             break;
                         case 1:
                             activity.setBackgroundColor(Color.GRAY);
                             break;
                     }
                 }
             }).show(); //siempre nos debemos acordar del método show para que se muestre el cuadro de diálogo

         }

        return super.onOptionsItemSelected(item); //devolvemos el super.onOptionsItemSelected(item)
    }
    public void cerrar(){
        //este método crea un cuadro de diálogo para preguntar si queremos cerrar la aplicación o no. En caso de darle a ok,
        // se cierra, en caso de cancel, no pasa nada
    new AlertDialog.Builder(this).setTitle("¿Realmente quieres cerrar la APP").setCancelable(false).setNegativeButton(android.R.string.cancel,null)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {
        @Override
        //aquí le decimos que cierre la aplicación si pulsa "ok"
        public void onClick(DialogInterface dialog, int which) {
            finish();//Cerramos la app
        }
    }).show(); //el método show para que se muestre el cuadro de diálogo
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //este método existe para en caso de que se mantenga pulsada la pantalla, te conviera la fuente del textview en función del
        //elemento que hayas tocado
        int id = item.getItemId();
        switch (id){
            case R.id.Grande:
                //cambia el tamaño de la letra del textview a 30dp
                pantalla.setTextSize(30f);
                break;
            case R.id.Normal:
                //cambia el tamaño de la letra del textview a 24dp
                pantalla.setTextSize(24f);
                break;
            case R.id.Pequeño:
                //cambia el tamaño de la letra del textview a 18dp
                pantalla.setTextSize(18f);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //inflamos el menú contextual para la selección del tamaño de fuentes
        getMenuInflater().inflate(R.menu.fuentes, menu);




    }
}