import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
public class Localidades {

    public static void Localidades(Connection con,int p){
        String sql,nombreLo,nombrePro,nombreCo;
        try{
            sql="select l.nombre " +
                    "from localidades l " +
                    "join provincias p using (n_provincia) " +
                    "join comunidades c using(id_comunidad)" +
                    "where l.poblacion>? and c.nombre in (select nombre from comunidades_elegidas)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,p);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                nombreLo=rs.getString("l.nombre");
                Localidades.meter(con,nombreLo);
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public static void meter(Connection con,String nombre){
        String sql;
        try{
            sql="insert into juego_localidades(nombre,aciertos,fallos) values(?,?,?)";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1,nombre);
            ps.setInt(2,0);
            ps.setInt(3,0);
            ps.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public static String borrarContenido(Connection con){
        String sql;
        try{
            sql="delete  from juego_localidades";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.executeUpdate();
            return "tabla borrada";

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return "no se ha podido borrar";
    }

    public static int comprobarLocalidades(Connection con,int p){
        String sql;
        int n;
        try {
            sql = "select count(*) as numero " +
                    "from localidades l " +
                    "join provincias p using (n_provincia) " +
                    "join comunidades c using(id_comunidad)" +
                    "where l.poblacion>? and c.nombre in (select nombre from comunidades_elegidas)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                n=rs.getInt("numero");
                return n;
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return -1;
    }

    public static String aleatorio(Connection con){
        String sql,nombre;
        try{
            sql="select nombre\n" +
                    "from juego_localidades\n" +
                    "where aciertos=0\n" +
                    "order by rand()\n" +
                    "limit 1";
            PreparedStatement ps= con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                nombre=rs.getString("nombre");
                System.out.println(nombre);
                return nombre;
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return null;
    }


    public static int verificar(String localidad,String provincia,Connection con) {
        String sql, pro;
        try {
            sql = "select p.nombre from localidades l join provincias p using(n_provincia) where l.nombre=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, localidad);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pro = rs.getString("p.nombre");
                if (pro.equals(provincia)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return -1;
    }

    public static void acierto(Connection con,String localidad){
        String sql;
        try{
            sql="update juego_localidades set aciertos=1 where nombre=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,localidad);
            ps.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public static void fallo(Connection con,String localidad){
        String sql;
        try{
            sql="update juego_localidades set fallos=(fallos+1) where nombre=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,localidad);
            ps.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public static void numeroFallos(Connection con){
        String sql;
        int fallos;
        try{
            sql="select sum(fallos) fallos from juego_localidades where aciertos=0";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                fallos=rs.getInt("fallos");
                System.out.println(fallos);
            }
        }catch (SQLException sqle){
           sqle.printStackTrace();
        }
    }

    public static void localidadesAcertadas(Connection con){
        String sql;
        String nombre;
        try{
            sql="select nombre from juego_localidades where aciertos=1";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                nombre=rs.getString("nombre");
                System.out.println(nombre);
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
}

