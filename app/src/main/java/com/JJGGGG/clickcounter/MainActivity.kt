package com.JJGGGG.clickcounter

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.JJGGGG.clickcounter.ui.theme.ClickCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //android:screenOrientation="portrait" supuestamente esto esta depercated
        //desde la version 15 para arriba, se cambia po la siguiente linea
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        enableEdgeToEdge()
        setContent {
            ClickCounterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(innerPadding)
                }
            }
        }
    }
}

@Composable
fun Content(padding: PaddingValues){
    var setsJ1 by remember { mutableIntStateOf(0) }
    var setsJ2 by remember { mutableIntStateOf(0) }

    var gamesJ1 by remember { mutableIntStateOf(0) }
    var gamesJ2 by remember { mutableIntStateOf(0) }
    var tieBreak by remember { mutableStateOf(false) } //TODO tie break

    var pointsJ1 by remember { mutableIntStateOf(0) }
    var pointsJ2 by remember { mutableIntStateOf(0) }
    var deuceJ1 by remember { mutableStateOf(false) }
    var deuceJ2 by remember { mutableStateOf(false) }

    var desactivateButton by remember { mutableStateOf(false) }
    var desactivateButtonReset by remember { mutableStateOf(false) }


    //funcion para resetar juego
    fun resetGame() {
        pointsJ1=0
        pointsJ2=0
        gamesJ1=0
        gamesJ2=0
        tieBreak=false
        setsJ1=0
        setsJ2=0
        desactivateButton=false
        desactivateButtonReset=true
        deuceJ1=false
        deuceJ2=false

    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        //BackgroundColorSpan = colorResource(ID = R.color.purple_500)

    ) {
        //creamos un row para cada encabezado con el texto sets-juegos-setactual
        TitularSection(stringResource(R.string.SETS))
        //con el deuceJ1/2 cambiamos a subrayado para que se sepa si tiene ventaja o no q jugador
        MarcadorColumnas(valorJ1 = setsJ1, valorJ2 = setsJ2, deuceJ1=deuceJ1, deuceJ2=deuceJ2)

        TitularSection(stringResource(R.string.GAMES))

        MarcadorColumnas(valorJ1 = gamesJ1, valorJ2 = gamesJ2, deuceJ1=deuceJ1, deuceJ2=deuceJ2)

        TitularSection(stringResource(R.string.set_actual))

        MarcadorColumnas(valorJ1 = pointsJ1, valorJ2 = pointsJ2, deuceJ1=deuceJ1, deuceJ2=deuceJ2)


        Row (


        ){
            // BOTON PARA MARCAR ++ PARA EL J1
            Button(onClick = {
                //cada vez que se inicia la partida, el boton de reser ready
                //se indica true cada vez que anota un jugador
                desactivateButtonReset=false

                if(!tieBreak) {
                        if (pointsJ1 == 40 && pointsJ2 < 40) {//GANAR JUEGO
                            //ganar sets si tiene mas de 2 de diferencia con 6 o 7
                            gamesJ1++
                            if(gamesJ1==6 && gamesJ2<=4 || gamesJ1==7 && gamesJ2<=5){
                                setsJ1++
                                gamesJ1 = 0
                                gamesJ2 = 0
                                pointsJ1 = 0
                                pointsJ2 = 0
                                //SI LOS SETS SON 2, YA HA GANADO EL J1, RESETEAMOS TODO
                                if (setsJ1 == 2) {
                                    //gana el jugador 1 desactivamos los botones
                                    Log.i("GANAJ1", "HA GANADO EL JUGADOR j1 POR TENER $setsJ1 sets")
                                    desactivateButton = true
                                    /*setsJ1 = 0
                                    setsJ2 = 0
                                    gamesJ1 = 0
                                    gamesJ2 = 0
                                    pointsJ1 = 0
                                    pointsJ2 = 0*/
                                }
                            }else{
                                pointsJ1 = 0
                                pointsJ2 = 0
                            }

                        } else if (pointsJ1 == 40 && pointsJ2 == 40) {
                            if(deuceJ2){
                                deuceJ2=false
                            }else if(deuceJ1){
                                gamesJ1++
                                pointsJ1=0
                                pointsJ2=0
                                deuceJ1=false
                            } else{
                                deuceJ1=true
                                //TODO PONER ADV O ALGO PARA SABER Q TIENE VENTAJA
                            }
                        } else {
                            pointsJ1 += 15
                            if (pointsJ1 == 45) {
                                pointsJ1 = 40
                            }
                        }
                        if (gamesJ1 == 6 && gamesJ2 == 6) {
                            tieBreak = true
                        }
                    }else{

                        //si es true, iniciamos el tiebreak sumar a a 1 cuando sea mayor a 2.
                        //gana un set el jugador q tenga ventaja de 2
                        pointsJ1++
                        Log.i("puntos", "PuntosJ1: $pointsJ1 y J2 puntos: $pointsJ2")
                        if(pointsJ1>=7 && pointsJ1-pointsJ2>=2){
                            tieBreak=false
                            pointsJ1=0
                            pointsJ2=0
                            gamesJ1=0
                            gamesJ2=0
                            setsJ1++
                        }
                }



                Log.i("onClick", "PULSADO BOTON $pointsJ1")
            },
                //desactivar el boton al ganar, activar al resetear
                enabled = !desactivateButton,

            ) {

                Text(
                    text = stringResource(R.string.point_A),
                    fontSize = dimensionResource(id = R.dimen.button_text).value.sp,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.button_padding))
                )



            }
            //BOTONES DE MARCAR ++ para el J2
            Button(onClick = {
                desactivateButtonReset=false

                if (!tieBreak) {
                    if (pointsJ2 == 40 && pointsJ1 < 40) {        // GANAR JUEGO (antes J2)
                        gamesJ2++
                        if (gamesJ2 == 6 && gamesJ1 <= 4 || gamesJ2 == 7 && gamesJ1 <= 5) {
                            setsJ2++
                            gamesJ1 = 0
                            gamesJ2 = 0
                            pointsJ1 = 0
                            pointsJ2 = 0

                            // SI LOS SETS SON 2, YA HA GANADO EL J2
                            if (setsJ2 == 2) {
                                Log.i("GANAJ2", "HA GANADO EL JUGADOR J2 POR TENER $setsJ2 sets")
                                desactivateButton = true
                            }
                        } else {
                            pointsJ1 = 0
                            pointsJ2 = 0
                        }

                    } else if (pointsJ2 == 40 && pointsJ1 == 40) {
                        if (deuceJ1) {
                            deuceJ1 = false
                        } else if (deuceJ2) {
                            gamesJ2++
                            pointsJ1 = 0
                            pointsJ2 = 0
                            deuceJ2 = false
                        } else {
                            deuceJ2 = true
                        }

                    } else {
                        pointsJ2 += 15
                        if (pointsJ2 == 45) pointsJ2 = 40
                    }
                    //tie break, los puntos ya no sumasn de +15
                    //sumaran de uno a uno, resetamos a 0 los puntos
                    if (gamesJ1 == 6 && gamesJ2 == 6) {
                        tieBreak = true
                        pointsJ1= 0
                        pointsJ2= 0

                    }

                } else {
                    pointsJ2++
                    Log.i("puntos", "PuntosJ1: $pointsJ1 y J2 puntos: $pointsJ2")
                    if(pointsJ2>=7 && pointsJ2-pointsJ1>=2){
                        tieBreak=false
                        pointsJ1=0
                        pointsJ2=0
                        gamesJ1=0
                        gamesJ2=0
                        setsJ2++
                    }
                }

                Log.i("onClick", "PULSADO BOTON $pointsJ2")
            },
                //desactivar el boton al ganar, activar al resetear
                enabled = !desactivateButton,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.color_J2)))

            {

                Text(
                    text = stringResource(R.string.point_B),
                    fontSize = dimensionResource(id = R.dimen.button_text).value.sp,
                    color = colorResource(id = R.color.black),

                    modifier = Modifier.padding(dimensionResource(id = R.dimen.button_padding))
                )
            }
        }

        //BOTON DE RESET

        Button(
                onClick = {resetGame()},
                enabled = !desactivateButtonReset, //
                modifier = Modifier.fillMaxSize(), //tod el boton rojo
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Color_button_reset)))

        {
            Text(
                    text = stringResource(R.string.button_reset_text),
                    fontSize = dimensionResource(id = R.dimen.button_text).value.sp,
                    color = colorResource(id = R.color.white),

                )
        }

    }




}




@Composable
fun MarcadorColumnas(valorJ1: Int, valorJ2: Int, deuceJ1: Boolean, deuceJ2: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),//separar los lados izq derecha centrado
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text( //texto con su puntuacion y resources por defecto
            fontWeight = FontWeight.Bold,
            text = valorJ1.toString(),
            style = TextStyle( //con el deuceJ1/2 cambiamos a subrayado para que se sepa si tiene ventaja
                textDecoration = if (deuceJ1) TextDecoration.Underline else TextDecoration.None
            ),
            color = colorResource(id = R.color.color_J1),
            fontSize = dimensionResource(id = R.dimen.text_size_marcadores).value.sp,
            )
        Text(
            fontWeight = FontWeight.Bold,
            text = valorJ2.toString(),
            style = TextStyle(
                textDecoration = if (deuceJ2) TextDecoration.Underline else TextDecoration.None
            ),
            color = colorResource(id = R.color.color_J2),
            fontSize = dimensionResource(id = R.dimen.text_size_marcadores).value.sp

        )
    }
}

@Composable
fun TitularSection(texto: String) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.Background_sets)),

        contentAlignment = Alignment.Center
    ) {
        Text(texto, color = Color.White, fontSize = 25.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClickCounterTheme {
        Scaffold (modifier = Modifier.fillMaxSize()){ innerPadding ->
            Content(innerPadding)
        }
    }
}

