import java.sql.Connection;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner teclado=new Scanner(System.in);
        Connection con;
        int poblacion;
        String comunidad,provincia,localidad,seguir;
        boolean lectura=true,lectura1=true,lectura2=true;
        if (GestorConexion.crearConexion("geografia", "geografia1", "A12345a")) {
            con = GestorConexion.getConexion();
            Localidades.borrarContenido(con);
            Comunidades.borrarContenido(con);
            System.out.println("indica la poblacion");
            poblacion=Integer.parseInt(teclado.nextLine());
            while(lectura){
                System.out.println("dime las comunidades, para meter todas di todas, para salir di salir");
                comunidad=teclado.nextLine();
                if(comunidad.equals("todas")){
                    Comunidades.borrarContenido(con);
                    Comunidades.todas(con);
                    lectura=false;
                }else if(comunidad.equals("salir")){
                    lectura=false;
                }else{
                    if(Comunidades.comprobarComunidad(con,comunidad)){
                        Comunidades.meterComunidad(con,comunidad);
                    }else{
                        System.out.println("esa comunidad no existe");
                    }
                }
            }
            if(Localidades.comprobarLocalidades(con,poblacion)>=50){
                Localidades.Localidades(con,poblacion);
                lectura1=true;
                while(lectura1) {
                    localidad=Localidades.aleatorio(con);
                    lectura2=true;
                    while(lectura2){
                        System.out.println("dime la provincia,para salir di salir");
                        provincia=teclado.nextLine();
                        if(provincia.equals("salir")) {
                            lectura2=false;
                        } else {
                            if (Localidades.verificar(localidad, provincia, con) == 1) {
                                System.out.println("has acertado");
                                Localidades.acierto(con,localidad);
                                lectura2=false;
                            } else if (Localidades.verificar(localidad, provincia, con) == 0) {
                                System.out.println("has fallado");
                                Localidades.fallo(con,localidad);
                            } else {
                                System.out.println("ha habido un error");
                            }

                        }
                    }
                    System.out.println("quieres seguir jugando s/n");
                    seguir=teclado.nextLine();
                    if(seguir.equals("n")){
                        System.out.println("fallos totales");
                        Localidades.numeroFallos(con);
                        System.out.println("Localidades acertadas");
                        Localidades.localidadesAcertadas(con);
                        lectura1=false;
                    }else if(!seguir.equals("s")){
                        System.out.println("error");
                    }

                }
            }else{
                System.out.println("no hay 50 localidades con esa población");
            }

        }else{
            System.out.println(GestorConexion.getError());
        }
    }
}
