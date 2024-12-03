package actsnsiena;

import java.sql.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Actsnsiena {

    public static void main(String[] args) {
        // Obtén la configuración desde un archivo o variables de entorno
        String url = System.getenv("SAP_HANA_URL");
        String user = System.getenv("SAP_HANA_USER");
        String password = System.getenv("SAP_HANA_PASSWORD");
        String resultFilePath = "C:\\Actualizar_SN_Codesser\\Datos\\resultado.txt";
        String dtwPath = "C:\\Actualizar_SN_Codesser\\Datos\\updateSNsienacodesser.bat";

        // Verifica que las credenciales estén configuradas
        if (url == null || user == null || password == null) {
            System.err.println("Error: Las credenciales de conexión no están configuradas.");
            return;
        }

        // Consulta SQL
        String query = "SELECT "
                + "\"CardCode\", "
                + "CASE WHEN \"GroupCode\" IN (102, 104, 105) THEN 'tNO' ELSE NULL END AS \"WTLiable\", "
                + "CASE WHEN \"GroupCode\" = 109 THEN 'W1' ELSE NULL END AS \"WTCode\", "
                + "CASE WHEN \"GroupCode\" IN (102, 104) THEN 'IVA_Exe' ELSE NULL END AS \"VatGroupLatinAmerica\", "
                + "CASE WHEN \"GroupCode\" = 108 THEN '21702005' ELSE NULL END AS \"DebPayAcct\" "
                + "FROM \"OCRD\" "
                + "WHERE \"UserSign\" = 276 "
                + "AND \"GroupCode\" IN (102, 104, 105, 108)";

        // Conectar y ejecutar consulta
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             BufferedWriter writer = new BufferedWriter(new FileWriter(resultFilePath))) {

            // Verifica permisos del archivo
            if (!Files.isWritable(Paths.get(resultFilePath))) {
                System.err.println("Error: No se tienen permisos para escribir en el archivo de resultado.");
                return;
            }

            // Escribir encabezado del archivo
            writer.write("CardCode\tWTLiable\tWTCode\tVatGroupLatinAmerica\tDebPayAcct");
            writer.newLine();

            // Escribir resultados
            while (rs.next()) {
                writer.write(String.join("\t",
                        rs.getString("CardCode"),
                        rs.getString("WTLiable"),
                        rs.getString("WTCode"),
                        rs.getString("VatGroupLatinAmerica"),
                        rs.getString("DebPayAcct")));
                writer.newLine();
            }
            System.out.println("Archivo TXT generado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error de conexión o consulta: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de archivo: " + e.getMessage());
        }

        // Ejecutar el archivo .bat
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(dtwPath);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            System.out.println("Data Transfer Workbench ejecutado exitosamente.");

            int exitCode = process.waitFor();
            System.out.println("DTW finalizó con código de salida: " + exitCode);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al ejecutar el DTW: " + e.getMessage());
        }
    }
}
