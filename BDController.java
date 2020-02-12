import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BDController {
	//Atributo necesario para crear la conexion con la BD
	private Connection conexion;
	
	
	BDController (){
		try {
			this.conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/MUSICA20", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error en el constructor vacio del BDController");
			e.printStackTrace();
		}
	}


	public Connection getConexion() {
		return conexion;
	}


	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}
	
	public ArrayList<Artista> artistas(){
		ArrayList<Artista> artistas= new ArrayList<Artista>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs=miStatement.executeQuery("select *from artista");
			while(rs.next()==true) {
				Artista art= new Artista(rs.getString("dni"),rs.getString("nombre"));
				artistas.add(art);
			}
			miStatement.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return artistas;
	}
	
	public ArrayList<Artista> buscarletradeartistas(String letra){
		ArrayList<Artista> artistas= new ArrayList<Artista>();
		
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs=miStatement.executeQuery("SELECT * FROM artista WHERE nombre LIKE'"+letra+"%'");
			while(rs.next()==true) {
				Artista art= new Artista(rs.getString("dni"),rs.getString("nombre"));
				artistas.add(art);
			}
			miStatement.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return artistas;
		
	}
	
	public void artistaalta(String dni, String nombre){
		 try {
			 	Statement miStatement = this.conexion.createStatement();
				String cadenaSQL = "insert into `artista` VALUES ('"+dni+"','" +nombre+"')";
				miStatement.executeUpdate(cadenaSQL);
				miStatement.close();
		 	}catch (SQLException e) {
		 		// TODO Auto-generated catch block
		 		System.out.println("Error en metodo todos artistas");
		 		e.printStackTrace();
	}
		
	}
	
	public void artistabaja(String dni) {
		try {
		 	Statement miStatement = this.conexion.createStatement();
			String cadenaSQL = "delete from artista where nombre LIKE'"+dni+"'";
			miStatement.executeUpdate(cadenaSQL);
			miStatement.close();
	 	}catch (SQLException e) {
	 		// TODO Auto-generated catch block
	 		System.out.println("Error en metodo todos artistas");
	 		e.printStackTrace();
}
	}
	
	public void mostrarcanciones() {
		try {
		 	Statement miStatement = this.conexion.createStatement();
			String cadenaSQL = "select * from cancion";
			miStatement.executeUpdate(cadenaSQL);
			miStatement.close();
	 	}catch (SQLException e) {
	 		// TODO Auto-generated catch block
	 		System.out.println("Error en metodo todos artistas");
	 		e.printStackTrace();
}
	}
	
	public ArrayList<Artista> listadoArtistasPorNumero(String numero) {
		ArrayList<Artista> artistas = new ArrayList<Artista>();
		// Creo el objeto tipo statement para poder hacer la consulta
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs = miStatement.executeQuery("Select * from artista where dni like '%" + numero + "';");
			while (rs.next()) {
				artistas.add(new Artista(rs.getString("dni"), rs.getString("nombre")));
			}
			miStatement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistasPorNumero");
			e.printStackTrace();
		}
		return artistas;
	}

	public ArrayList<Cancion> listadoCanciones() {
		ArrayList<Cancion> canciones = new ArrayList<Cancion>();
		// Creo el objeto tipo statement para poder hacer la consulta
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs = miStatement.executeQuery("Select * from cancion");
			while (rs.next()) {
				canciones.add(new Cancion(rs.getString("cod"), rs.getString("titulo"), rs.getDouble("duracion")));
			}
			miStatement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return canciones;
	}
	
	public Cancion cancionmasLarga() {
		ArrayList<Cancion> canciones = new ArrayList<Cancion>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs = miStatement.executeQuery("Select * from cancion order by duracion desc limit 0,1");
			while (rs.next()) {
				canciones.add(new Cancion(rs.getString("cod"), rs.getString("titulo"), rs.getDouble("duracion")));
			}
			miStatement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return canciones.get(0);
	}
	
	public ArrayList<Cancion> cancionesMenorLongitud(double duracion){
		ArrayList<Cancion> canciones = new ArrayList<Cancion>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs = miStatement.executeQuery("Select * from cancion where duracion<" + duracion);
			while (rs.next()) {
				canciones.add(new Cancion(rs.getString("cod"), rs.getString("titulo"), rs.getDouble("duracion")));
			}
			miStatement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return canciones;
	}
	
	public ArrayList<Cancion> tituloscanciones(String disco){
		ArrayList<Cancion> canciones = new ArrayList<Cancion>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs = miStatement.executeQuery("select cancion.titulo from cancion, esta,disco where disco.nombre like '"+disco+"' and cancion.cod = esta.can and esta.cod = disco.cod order by cancion.titulo'");
			while (rs.next()) {
				canciones.add(new Cancion(rs.getString("titulo")));
			}
			miStatement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return canciones;
	}
	
	public boolean existeArtista( ArrayList<Artista> artis, String dni){
		boolean existe=false;
		try {
			Statement miStatement = this.conexion.createStatement();
			String cadena = "select *from artista where dni='"+dni+"';";
			ResultSet rs = miStatement.executeQuery(cadena);
			while(rs.next()) {
				existe=true;
			}
			miStatement.close();
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return existe;
		
	}
	
	public boolean existecancion( ArrayList<Cancion> cancis, int codigo){
		boolean existe=false;
		try {
			Statement miStatement = this.conexion.createStatement();
			String cadena = "select *from artista where cod="+codigo+";";
			ResultSet rs = miStatement.executeQuery(cadena);
			while(rs.next()) {
				existe=true;
			}
			miStatement.close();
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return existe;
		
	}
	
	public void altacancion(int codigo, String titulo, double duracion1){
		 try {
			 	Statement miStatement = this.conexion.createStatement();
				String cadenaSQL = "insert into `artista` VALUES ("+codigo+",'" +titulo+"',"+duracion1+")";
				miStatement.executeUpdate(cadenaSQL);
				miStatement.close();
		 	}catch (SQLException e) {
		 		// TODO Auto-generated catch block
		 		System.out.println("Error en metodo todos artistas");
		 		e.printStackTrace();
	}
		
	}
	
	public void cancionbaja(int codigo) {
		try {
		 	Statement miStatement = this.conexion.createStatement();
			String cadenaSQL = "delete from artista where nombre="+codigo;
			miStatement.executeUpdate(cadenaSQL);
			miStatement.close();
	 	}catch (SQLException e) {
	 		// TODO Auto-generated catch block
	 		System.out.println("Error en metodo todos artistas");
	 		e.printStackTrace();
}
	}
	
	public ArrayList<Grupo> listadogrupo(){
		ArrayList<Grupo> grupos= new ArrayList<Grupo>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs=miStatement.executeQuery("select *from grupo");
			while(rs.next()==true) {
				grupos.add(new Grupo(rs.getString("cod"),rs.getString("nombre"),rs.getString("fecha"),rs.getString("pais")));
			}
			miStatement.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return grupos;
	}
	
	public ArrayList<Disco> listadodiscos(){
		ArrayList<Disco> discos= new ArrayList<Disco>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs=miStatement.executeQuery("select *from disco");
			while(rs.next()==true) {
				discos.add(new Disco(rs.getString("cod"),rs.getString("nombre"),rs.getString("fecha"),rs.getString("cod_comp"),rs.getString("cod_gru")));
			}
			miStatement.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return discos;
	}
	
	public ArrayList<Artista> artistasdeungrupo(String nombre){
		ArrayList<Artista> artistas = new ArrayList<Artista>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs = miStatement.executeQuery("select *from artista where dni in(select dni from pertenece where cod in(select cod from grupo where nombre='"+nombre+"'));");
			while (rs.next()) {
				artistas.add(new Artista(rs.getString("nombre")));
			}
			miStatement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return artistas;
	}
	
	public void modificardisco(String nombre1, int codig_com){
		try {
			Statement miStatement = this.conexion.createStatement();
			String codigoSQL= "update disco set cod_comp="+codig_com+" where nombre='"+nombre1+"';";
			miStatement.executeUpdate(codigoSQL);
			miStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
	}
	
	public boolean existedisco(String nombre){
		boolean existe=false;
		try {
			Statement miStatement = this.conexion.createStatement();
			String cadena = "select * from disco where nombre='"+nombre+"';";
			ResultSet rs = miStatement.executeQuery(cadena);
			while(rs.next()) {
				existe=true;
			}
			miStatement.close();
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return existe;
		
	}
	
	public ArrayList<Grupo> datosdegrupo(String nombre){
		ArrayList<Grupo> grupos = new ArrayList<Grupo>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs = miStatement.executeQuery("select * from grupo where cod in(select cod_gru from club where nombre='"+nombre+"');");
			while (rs.next()) {
				grupos.add(new Grupo(rs.getString("cod"),rs.getString("nombre"),rs.getString("fecha"),rs.getString("pais")));
			}
			miStatement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return grupos;                                                                            
	}
	
	public ArrayList<Artista> listadodeartistassingrupo(){
		ArrayList<Artista> artistas = new ArrayList<Artista>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs = miStatement.executeQuery("select * from artista where dni in(select dni from pertenece where cod is null);");
			while (rs.next()) {
				artistas.add(new Artista(rs.getString("dni"),rs.getString("nombre")));
			}
			miStatement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return artistas;                                                                            
	}
	
	
	public ArrayList<Cancion> listadodecancionessindisco(){
		ArrayList<Cancion> canciones = new ArrayList<Cancion>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs = miStatement.executeQuery("select * from cancion where cod in(select can from esta where cod is null);");
			while (rs.next()) {
				canciones.add(new Cancion(rs.getString("cod"),rs.getString("titulo"),rs.getDouble("duracion")));
			}
			miStatement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en listadoArtistas");
			e.printStackTrace();
		}
		return canciones;
	}
	
	public ArrayList<Disco> discosdemascanciones(){
		ArrayList<Disco> discos= new ArrayList<Disco>();
		try {
			Statement miStatement = this.conexion.createStatement();
			ResultSet rs=miStatement.executeQuery("select *from disco where cod in(select cod from esta group by cod HAVING count(can)>3);");
			while(rs.next()==true) {
				discos.add(new Disco(rs.getString("cod"),rs.getString("nombre"),rs.getString("fecha"),rs.getString("cod_comp"),rs.getString("cod_gru")));
			}
			miStatement.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return discos;
	}
	
	
	
	
	
	
	
}