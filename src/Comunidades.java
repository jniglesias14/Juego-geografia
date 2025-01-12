import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Comunidades {
    public static String borrarContenido(Connection con){
        String sql;
        try{
            sql="delete  from comunidades_elegidas";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.executeUpdate();
            return "tabla borrada";

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return "no se ha podido borrar";
    }


    public static void todas(Connection con){
        String sql1,nombre;
        try{
            sql1="select nombre from comunidades";
            PreparedStatement ps=con.prepareStatement(sql1);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                nombre=rs.getString("nombre");
                Comunidades.meterComunidad(con,nombre);
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    public static void meterComunidad(Connection con,String comunidad){
        String sql;
        try{
            sql="insert into comunidades_elegidas(nombre) values(?)";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1,comunidad);
            ps.executeUpdate();
        }catch (SQLException sqle){
            if(sqle.getErrorCode()==1062){
                System.out.println("comunidad repetidad");
            }else {
                sqle.printStackTrace();
            }
        }
    }
    public static boolean comprobarComunidad(Connection con,String comunidad){
        String sql;
        try{
            sql="select nombre from comunidades where nombre=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,comunidad);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }


}
