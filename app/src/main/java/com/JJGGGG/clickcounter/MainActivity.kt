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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    var deuceJ1 by remember { mutableStateOf(false) }
    var deuceJ2 by remember { mutableStateOf(false) }

    var gamesJ1 by remember { mutableIntStateOf(0) }
    var gamesJ2 by remember { mutableIntStateOf(0) }
    var tieBreak by remember { mutableStateOf(false) } //TODO tie break
    var tieBreakJ1 by remember { mutableIntStateOf(0) }
    var tieBreakJ2 by remember { mutableIntStateOf(0) }


    var pointsJ1 by remember { mutableIntStateOf(0) }
    var pointsJ2 by remember { mutableIntStateOf(0) }

    var desactivarBoton by remember { mutableStateOf(false) }

    
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

        MarcadorColumnas(ValorJ1 = setsJ1, ValorJ2 = setsJ2)

        TitularSection(stringResource(R.string.GAMES))

        MarcadorColumnas(ValorJ1 = gamesJ1, ValorJ2 = gamesJ2)

        TitularSection(stringResource(R.string.set_actual))

        MarcadorColumnas(ValorJ1 = pointsJ1, ValorJ2 = pointsJ2)


        Row (
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            // BOTON PARA MARCAR ++ PARA EL J1
            Button(onClick = {


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
                                    desactivarBoton = true
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
                        tieBreakJ1++
                        if(tieBreakJ1>=7 && tieBreakJ1-2<=tieBreakJ2){
                            tieBreak=false
                            tieBreakJ1=0
                            tieBreakJ2=0
                            setsJ1++
                        }else{
                            tieBreakJ1++
                        }
                }





                Log.i("onClick", "PULSADO BOTON $pointsJ1")
            },
                //desactivar el boton al ganar, activar al resetear
                enabled = !desactivarBoton) {

                Text(
                    text = stringResource(R.string.clickme),
                    fontSize = dimensionResource(id = R.dimen.button_text).value.sp,
                    color = colorResource(id = R.color.teal_200),
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.button_padding))
                )



                //BOTONES DE MARCAR ++ para el J2
                Button(onClick = {

                },
                    //desactivar el boton al ganar, activar al resetear
                    enabled = !desactivarBoton) {

                    Text(
                        text = stringResource(R.string.clickme),
                        fontSize = dimensionResource(id = R.dimen.button_text).value.sp,
                        color = colorResource(id = R.color.teal_200),
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.button_padding))
                    )
                }

            }
        }

        //BOTON DE RESET
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                    .fillMaxSize()
                .padding(padding)

        ) { //BOTON de reset
            Button(onClick = {
                //TODO

            }) {

                Text(
                    text = stringResource(R.string.button_reset_text),
                    fontSize = dimensionResource(id = R.dimen.button_text).value.sp,
                    color = colorResource(id = R.color.teal_200),
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.button_padding))
                )
            }
        }
    }




}

@Composable
fun BotonAccion(texto: String, color: Color, onClick: () -> Unit) {
    TODO("Not yet implemented")
}


@Composable
fun MarcadorColumnas(ValorJ1: Int, ValorJ2: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),//separar los lados izq derecha centrado
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text( //texto con su puntuacion y resources por defecto
            text = ValorJ1.toString(),
            color = colorResource(id = R.color.color_J1),
            fontSize = dimensionResource(id = R.dimen.text_size_marcadores).value.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = ValorJ2.toString(),
            color = colorResource(id = R.color.color_J2),
            fontSize = dimensionResource(id = R.dimen.text_size_marcadores).value.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TitularSection(texto: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.Background_sets))
            .padding(vertical = 8.dp),
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

