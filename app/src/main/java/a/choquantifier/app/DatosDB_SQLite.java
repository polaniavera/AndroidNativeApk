package a.choquantifier.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import android.util.Log;
import android.database.Cursor;

public class DatosDB_SQLite extends SQLiteOpenHelper {

    static public final int VERSION_DB=61;
    static public final String NOMBRE_DB="basedatos";
    static public final int USU_NOEXISTE=0,USU_DIABETICO=1,USU_NODIABETICO=2,USU_ADMIN=3;

    public DatosDB_SQLite(Context context, String nombre, CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        baseDatosAll(db,1,1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        baseDatosAll(db,versionAnte,versionNue);
    }

    public void baseDatosAll(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists usuario");
        db.execSQL("drop table if exists parametrosmedicos");
        db.execSQL("drop table if exists alimentos");
        db.execSQL("drop table if exists tipo");
        db.execSQL("drop table if exists historial");
        db.execSQL("drop table if exists temporalmenu");

        db.execSQL("BEGIN TRANSACTION;");

        db.execSQL("CREATE TABLE [usuario] ([codigo] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,[diabetico] INTEGER DEFAULT '0' NOT NULL,[parentesco] TEXT  NOT NULL,[nombre] TEXT  NOT NULL,[nick] TEXT NOT NULL,[password] TEXT  NOT NULL,[sexo] TEXT  NOT NULL,[fechanacimiento] DATE  NOT NULL,[talla] INTEGER  NOT NULL,[peso] INTEGER  NOT NULL,[imc] FLOAT  NOT NULL);");
        db.execSQL("CREATE TABLE [parametrosmedicos] (  [parametrosmedicos_usuario] INTEGER  NOT NULL, [parametrosmedicos_hora] INTEGER DEFAULT '0' NOT NULL, [parametrosmedicos_sencibilidad] FLOAT DEFAULT '0' NULL,        [parametrosmedicos_ratio] FLOAT DEFAULT '0' NULL)");
        db.execSQL("CREATE TABLE [alimentos] ([codigo] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,[nombre] TEXT  NOT NULL,[tipo] TEXT  NULL,[factorcho] FLOAT  NULL,[factorfibra] FLOAT  NULL,[indiceglicemico] FLOAT  NULL, [factorgrasa] FLOAT  NULL, [factorproteina] FLOAT  NULL,[observaciones] TEXT  NULL,[editar] FLOAT DEFAULT '1' NOT NULL);");
        db.execSQL("CREATE TABLE [tipo] ([tipoa] NUMERIC  PRIMARY KEY NOT NULL,[nombre] TEXT  NOT NULL);");
        db.execSQL("CREATE TABLE [historial] ([historial_id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, [historial_usuario] INTEGER  NOT NULL, [historial_fecha] DATE  NULL, [historial_hora] TIME  NULL, [historial_gs_actual] INTEGER  NOT NULL, [historial_cho_totales] FLOAT  NOT NULL, [historial_bolo_alimenticio] REAL  NOT NULL, [historial_bolo_correccion] FLOAT  NOT NULL, [historial_bolo_total] FLOAT  NOT NULL );");
        db.execSQL("CREATE TABLE [temporalmenu] ([codigo] INTEGER  NOT NULL,[nombre] TEXT  NOT NULL,[porcion] FLOAT  NULL,[factorcho] FLOAT  NULL,[factorfibra] FLOAT  NULL,[indiceglicemico] FLOAT  NULL, [factorgrasa] FLOAT  NULL, [factorproteina] FLOAT  NULL);");

        //Se crea un usuario administrador por defecto
        db.execSQL("INSERT INTO usuario VALUES(1,3,'admin','administrador','a','1','M','1984-01-17',174,75,29.4);");

        db.execSQL("INSERT INTO alimentos VALUES(1,'Arveja Seca',1,0.3020,0.0215,25,0.0060,0.1230,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(2,'Fríjol Rojo ',1,0.3065,0.0760,35,0.0060,0.1125,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(3,'Garbanzo',1,0.2785,0.0870,35,0.0300,0.0980,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(4,'Lenteja',1,0.2855,0.1525,35,0.0050,0.1405,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(5,'Atún Fresco',2,0.0000,0.0000,0,0.0489,0.2326,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(6,'Carne de Cerdo Magra Cocida',2,0.0000,0.0000,0,0.0998,0.3745,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(7,'Chorizo',2,0.0000,0.0000,0,0.4024,0.3316,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(8,'Carne de Res Cocida Todos los Cortes Magra',2,0.0000,0.0000,0,0.1300,0.4300,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(9,'Cuajada',2,0.0610,0.0000,35,0.1800,0.1560,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(10,'Huevo Codorniz Entero',2,0.0040,0.0000,0,0.1111,0.1311,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(11,'Huevo Gallina Entero',2,0.0120,0.0000,0,0.1000,0.1250,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(12,'Jamón de Pollo',2,0.0000,0.0000,0,0.0733,0.1931,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(13,'Mortadela de Res y Cerdo',2,0.0315,0.0000,0,0.2573,0.1660,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(14,'Pechuga Cocida sin Piel',2,0.0000,0.0000,0,0.0300,0.2900,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(15,'Queso Entero Duro',2,0.0200,0.0000,35,0.3110,0.2500,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(16,'Salchicha de Res Cocida',2,0.0243,0.0000,0,0.2715,0.1423,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(17,'Salchichón Común',2,0.0113,0.0000,0,0.3557,0.2220,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(18,'Aguacate Pulpa sin Semilla',3,0.0740,0.0390,10,0.1530,0.0200,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(19,'Anón Pulpa sin Semilla',3,0.2540,0.0440,35,0.0030,0.0230,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(20,'Banano Bocadillo Pulpa',3,0.2740,0.0000,50,0.0010,0.0120,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(21,'Banano Común Pulpa',3,0.2340,0.0111,35,0.0051,0.0100,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(22,'Brevas con Cáscara',3,0.1920,0.0330,35,0.0030,0.0080,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(23,'Chontaduro Pulpa Cocida',3,0.3760,0.0000,30,0.0460,0.0333,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(24,'Ciruela Común',3,0.2080,0.0120,35,0.0010,0.0100,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(25,'Curuba Pulpa',3,0.0630,0.0000,35,0.0010,0.0060,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(26,'Durazno Amarillo con Cáscara',3,0.1200,0.0151,35,0.0011,0.0100,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(27,'Feijoa sin Cáscara',3,0.1191,0.0000,35,0.0000,0.0091,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(28,'Fresa',3,0.0691,0.0180,25,0.0040,0.0080,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(29,'Granadilla Pulpa con Semillas',3,0.1030,0.0590,30,0.0230,0.0250,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(30,'Granadilla Pulpa sin Semillas',3,0.1160,0.0000,30,0.0011,0.0111,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(31,'Guanábana Pulpa',3,0.1300,0.0330,35,0.0030,0.0110,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(32,'Guayaba Común',3,0.1710,0.0000,35,0.0290,0.0100,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(33,'Limón con Cáscara sin Semillas',3,0.1070,0.0470,7,0.0030,0.0120,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(34,'Mandarina sin Cáscara',3,0.0950,0.0180,30,0.0010,0.0070,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(35,'Mango Pulpa sin Cáscara',3,0.1700,0.0180,50,0.0031,0.0051,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(36,'Maní Seco Tostado con Sal',3,0.2150,0.0800,15,0.4970,0.2370,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(37,'Manzana con Cascara sin Semillas',3,0.1530,0.0200,35,0.0040,0.0020,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(38,'Melón Amarillo Pulpa',3,0.0840,0.0075,60,0.0031,0.0091,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(39,'Moras Pulpa',3,0.1280,0.0530,25,0.0040,0.0070,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(40,'Naranja sin Cáscara',3,0.1180,0.0211,35,0.0011,0.0091,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(41,'Níspero Pulpa sin Semilla',3,0.2200,0.0531,55,0.0111,0.0051,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(42,'Papaya Pulpa sin Semilla',3,0.0980,0.0180,55,0.0011,0.0060,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(43,'Papayuela Pulpa sin Semilla',3,0.0390,0.0000,45,0.0010,0.0070,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(44,'Pera con Cáscara',3,0.1510,0.0280,30,0.0040,0.0040,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(45,'Piña Pulpa sin Corazón',3,0.1240,0.0120,45,0.0040,0.0040,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(46,'Pitahaya Amarilla Pulpa',3,0.1320,0.0210,50,0.0010,0.0040,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(47,'Sandía o Patilla Pulpa',3,0.0720,0.0040,75,0.0040,0.0060,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(48,'Tamarindo Pulpa Concentrada',3,0.6130,0.0510,45,0.0040,0.0540,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(49,'Tomate de Árbol Pulpa sin Semilla',3,0.0700,0.0000,30,0.0010,0.0140,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(50,'Uchuva Entera',3,0.1960,0.0000,15,0.0020,0.0010,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(51,'Uva Europea con Cáscara',3,0.1780,0.0100,45,0.0060,0.0070,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(52,'Uva Negra con Cáscara',3,0.0960,0.0054,45,0.0000,0.0040,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(53,'Uva Verde con Cáscara',3,0.0811,0.0051,45,0.0000,0.0051,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(54,'Zapote Pulpa',3,0.1240,0.0130,40,0.0010,0.0110,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(55,'Apio Tallo',4,0.0431,0.0131,25,0.0011,0.0071,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(56,'Cebolla Cabezona',4,0.0860,0.0171,15,0.0020,0.0120,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(57,'Cebolla Común Hojas y Tallo',4,0.0730,0.0000,15,0.0020,0.0180,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(58,'Champiñones',4,0.0469,0.0119,15,0.0040,0.0210,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(59,'Col Hojas',4,0.0571,0.0360,15,0.0040,0.0251,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(60,'Coliflor',4,0.0480,0.0180,15,0.0011,0.0300,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(61,'Ensalada Cocida',4,0.1026,0.0301,45,0.0033,0.0176,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(62,'Ensalada Cruda',4,0.0500,0.0145,25,0.0028,0.0116,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(63,'Espárragos',4,0.0451,0.0171,25,0.0020,0.0231,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(64,'Espinaca',4,0.0350,0.0180,15,0.0040,0.0290,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(65,'Habichuela',4,0.0710,0.0340,30,0.0010,0.0180,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(66,'Jengibre Raíz',4,0.1510,0.0200,20,0.0070,0.0170,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(67,'Lechuga Batavia',4,0.0210,0.0120,15,0.0020,0.0100,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(68,'Lechuga Común',4,0.0350,0.0190,15,0.0030,0.0130,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(69,'Maíz Tierno o Choclo',4,0.1900,0.0320,65,0.0120,0.0320,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(70,'Pimentón Rojo',4,0.0640,0.0000,15,0.0020,0.0090,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(71,'Remolacha Cocida',4,0.0960,0.0260,65,0.0020,0.0160,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(72,'Repollo Común',4,0.0540,0.0230,15,0.0030,0.0140,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(73,'Repollo Morado',4,0.0610,0.0200,15,0.0030,0.0140,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(74,'Tomate Rojo Maduro',4,0.0460,0.0110,35,0.0030,0.0090,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(75,'Tomate Verde',4,0.0511,0.0171,30,0.0020,0.0120,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(76,'Zanahoria Cocida',4,0.1010,0.0240,85,0.0020,0.0100,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(77,'Almojábana de Maíz',5,0.3060,0.0000,80,0.1230,0.1300,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(78,'Almidón de Maíz (Maicena)',5,0.9130,0.0090,85,0.0010,0.0030,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(79,'Arepa con Queso',5,0.2900,0.0000,65,0.0840,0.0480,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(80,'Arepa de Maíz Blanco Trillado',5,0.3730,0.0000,70,0.0050,0.0410,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(81,'Arepa Plana Delgada',5,0.4020,0.0000,70,0.0060,0.0380,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(82,'Arroz Blanco Cocido con Sal',5,0.2817,0.0040,70,0.0028,0.0269,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(83,'Arroz Chino Cocido',5,0.3750,0.0290,50,0.0207,0.1171,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(84,'Arroz con Pollo',5,0.3750,0.0290,50,0.0137,0.1321,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(85,'Avena Cruda en Hojuelas Fortificada',5,0.6400,0.1090,40,0.0610,0.1550,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(86,'Buñuelo',5,0.2660,0.0100,75,0.1234,0.0361,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(87,'Calado o Tostada',5,0.5440,0.0251,55,0.0400,0.0900,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(88,'Calado o Tostada Integral',5,0.5170,0.0740,45,0.0480,0.1090,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(89,'Cebada Perlada',5,0.7770,0.1560,45,0.0120,0.0990,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(90,'Croissant Mantequilla o Queso',5,0.4700,0.0260,60,0.2092,0.0920,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(91,'Cuchuco de Cebada',5,0.7780,0.0000,85,0.0070,0.0900,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(92,'Cuchuco de Trigo',5,0.7060,0.0000,85,0.0120,0.1371,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(93,'Galletas Saltinas o de Soda',5,0.7152,0.0300,65,0.1180,0.0920,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(94,'Granola con Avena Tostada',5,0.6680,0.0300,60,0.1740,0.1020,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(95,'Harina de Trigo Nacional',5,0.7320,0.0000,85,0.0100,0.1180,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(96,'Maíz Amarillo Grano Entero',5,0.7430,0.0000,65,0.0470,0.0940,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(97,'Mogolla',5,0.5771,0.0000,85,0.0451,0.0971,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(98,'Pan Blanco Preparación Comercial',5,0.4950,0.0230,85,0.0360,0.0820,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(99,'Pan con Queso',5,0.4580,0.0000,65,0.0700,0.1040,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(100,'Pan de Centeno',5,0.4830,0.0580,65,0.0330,0.0850,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(101,'Pan de Yuca',5,0.4500,0.0000,80,0.1220,0.1580,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(102,'Pan Francés',5,0.5190,0.0300,65,0.0300,0.0880,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(103,'Pan Integral',5,0.5100,0.0691,55,0.0200,0.0811,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(104,'Pan Pita o Árabe',5,0.5570,0.0220,60,0.0120,0.0910,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(105,'Pan Tajado Bimbo',5,0.4808,0.0192,60,0.0288,0.1154,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(106,'Pasta Alimenticia Cocida Cualquier Tipo',5,0.7710,0.0000,50,0.0040,0.1300,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(107,'Empanada',6,0.3030,0.0000,78,0.1580,0.0550,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(108,'Hamburguesa Med. Carne y Pan',6,0.3390,0.0000,85,0.1310,0.1370,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(109,'Hambur. Med. Queso y Verdura',6,0.1830,0.0000,80,0.1290,0.1160,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(110,'Mazorca con Mantequilla',6,0.2190,0.0000,70,0.0240,0.0310,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(111,'Perro Caliente con Papa Triturada',6,0.1840,0.0000,85,0.1480,0.1060,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(112,'Pizza con Queso',6,0.3250,0.0000,70,0.0510,0.1220,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(113,'Taco',6,0.1560,0.0000,80,0.1200,0.1210,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(114,'Papas a la Francesa Fritas en Aceite',7,0.3860,0.0350,95,0.1610,0.0400,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(115,'Papa Cocida con Cáscara',7,0.1930,0.0160,65,0.0010,0.0190,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(116,'Papa Criolla Cocida con Cáscara',7,0.2160,0.0000,70,0.0010,0.0250,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(117,'Puré de Papa Casero con Leche',7,0.1757,0.0150,80,0.0057,0.0191,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(118,'Plátano Coli o Guineo Cocido sin Cáscara',7,0.1869,0.0000,70,0.0006,0.0106,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(119,'Plátano Hartón Cocido sin Cáscara',7,0.3780,0.0231,70,0.0020,0.0120,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(120,'Yuca Blanca Cocida',7,0.2319,0.0169,55,0.0006,0.0056,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(121,'Kumis',8,0.1444,0.0000,40,0.0039,0.0339,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(122,'Nectar de Durazno',8,0.1494,0.0000,65,0.0000,0.0009,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(123,'Coca Cola',8,0.1035,0.0000,88,0.0000,0.0000,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(124,'Cerveza 4% Alcohol',8,0.0438,0.0000,119,0.0000,0.0019,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(125,'Vino de Mesa Blanco 9,3% Alcohol',8,0.0080,0.0000,80,0.0000,0.0010,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(126,'Leche de Vaca Descre. Pasteurizada',8,0.0473,0.0000,32,0.0019,0.0328,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(127,'Leche de Vaca Entera Pasteurizada',8,0.0456,0.0000,27,0.0320,0.0320,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(128,'Azúcar en Polvo',9,0.9950,0.0000,100,0.0010,0.0000,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(129,'Bocadillo Veleño',9,0.7900,0.0000,70,0.0010,0.0030,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(130,'Cocada de Panela',9,0.8192,0.0000,75,0.0820,0.0160,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(131,'Cucas',9,0.8192,0.0000,65,0.0300,0.0832,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(132,'Gelatina Saborizada con Azúcar',9,0.8872,0.0000,70,0.0000,0.0940,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(133,'Helado de Vainilla Suave',9,0.2340,0.0000,65,0.0590,0.0380,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(134,'Mantecada',9,0.5600,0.0000,65,0.1632,0.0792,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(135,'Masmelos',9,0.8040,0.0000,75,0.0000,0.0200,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(136,'Mayonesa Comercial',9,0.0280,0.0000,60,0.7940,0.0120,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(137,'Mermelada ',9,0.7051,0.0000,65,0.0031,0.0051,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(138,'Miel de Abejas',9,0.7980,0.0000,80,0.0020,0.0060,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(139,'Panela',9,0.8600,0.0000,65,0.0100,0.0052,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(140,'Panelitas de Leche de Vaca',9,0.8300,0.0000,65,0.0180,0.0292,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(141,'Ponquecitos',9,0.4432,0.0000,70,0.1932,0.0532,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(142,'Salsa de Tomate',9,0.0720,0.0000,45,0.0020,0.0140,'',0.0);");
        db.execSQL("INSERT INTO alimentos VALUES(143,'Uvas Pasas',9,0.7910,0.0000,65,0.0050,0.0320,'',0.0);");

        //Se adiciona un alimento creado por defecto
        db.execSQL("INSERT INTO alimentos VALUES(144,'Chunchulla',10,0.0,0.0,65.0,0.999,0.0,'',0.0);");

        db.execSQL("INSERT INTO tipo VALUES(1,'Leguminosas');");
        db.execSQL("INSERT INTO tipo VALUES(2,'Alimentos Protéicos');");
        db.execSQL("INSERT INTO tipo VALUES(3,'Frutas');");
        db.execSQL("INSERT INTO tipo VALUES(4,'Verduras');");
        db.execSQL("INSERT INTO tipo VALUES(5,'Cereales y Derivados');");
        db.execSQL("INSERT INTO tipo VALUES(6,'Comidas Rápidas');");
        db.execSQL("INSERT INTO tipo VALUES(7,'Tubérculos');");
        db.execSQL("INSERT INTO tipo VALUES(8,'Bebidas');");
        db.execSQL("INSERT INTO tipo VALUES(9,'Otros');");
        db.execSQL("INSERT INTO tipo VALUES(10,'Alimentos Creados');");

        db.execSQL("COMMIT;");
    }

    public ArrayList<ArrayList<String>> getAllTipos() {

        ArrayList<ArrayList<String>> tiposArray = new ArrayList<ArrayList<String>>();
        ArrayList<String> idArr = new ArrayList<String>();
        ArrayList<String> nombreArr = new ArrayList<String>();

        String selectQuery = "SELECT tipoa AS _id, nombre as name FROM tipo ORDER BY _id ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do
            {   idArr.add(cursor.getString(0));
                nombreArr.add(cursor.getString(1));
            }   while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        tiposArray.add(idArr);
        tiposArray.add(nombreArr);

        return tiposArray;
    }

    //Método no se esta utilizando
    public Set<String> getAllIndustrializados() {
        Set<String> set = new HashSet<String>();
        String selectQuery = "SELECT codigo AS _id, codigo||\"- \"||nombre as name FROM alimentos WHERE tipo='99' ORDER BY _id ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do
            {   set.add(cursor.getString(1));
            }   while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return set;
    }

    public ArrayList<ArrayList<String>> getAllNoIndustrializados(String Tipo) {
        Tipo=Tipo.trim();

        ArrayList<ArrayList<String>> alimentosArray = new ArrayList<ArrayList<String>>();
        ArrayList<String> idArr = new ArrayList<String>();
        ArrayList<String> nombreArr = new ArrayList<String>();

        String selectQuery = "SELECT codigo AS _id, nombre as name FROM alimentos WHERE tipo='"+Tipo+"' ORDER BY _id ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do
            {   idArr.add(cursor.getString(0));
                nombreArr.add(cursor.getString(1));
            }   while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        alimentosArray.add(idArr);
        alimentosArray.add(nombreArr);

        return alimentosArray;
    }

    public String AAA="";
    public String BBB="";
    public void getParametrosMedicosusuarioHora(int usuario, int hora) {

        if(usuario>0 && (hora>=0 && hora<=23))
        {   String selectQuery =
                    "SELECT parametrosmedicos_ratio, parametrosmedicos_sencibilidad  " +
                    "FROM parametrosmedicos " +
                    "WHERE (parametrosmedicos_usuario="+ Integer.toString(usuario) + " AND  parametrosmedicos_hora="+ Integer.toString(hora) + " )";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                AAA=cursor.getString(0);
                BBB=cursor.getString(1);
            }
            cursor.close();
            db.close();
        }
    }

    public void setParametrosMedicosusuarioHora(int usuario, int hora, int ratio, double sensibilidad) {
        String selectQuery = "UPDATE parametrosmedicos SET " +
                " parametrosmedicos_sencibilidad = " + Double.toString(sensibilidad)  + " , "+
                " parametrosmedicos_ratio        = " + Integer.toString(ratio)  +
                " WHERE (parametrosmedicos_usuario="+Integer.toString(usuario)+ " AND parametrosmedicos_hora="+Integer.toString(hora)+")";
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(selectQuery);
            db.close();
    }

    public void setPassword(int usuario, String newPassword) {
        String selectQuery = "UPDATE usuario SET password = " + newPassword +  " WHERE (codigo="+Integer.toString(usuario)+")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
        db.close();
    }

    public void deleteUsuario(int codigo) {
        String selectQuery = "DELETE FROM usuario WHERE codigo = " + Integer.toString(codigo);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
        db.close();
    }

    public void setPesoTalla(int usuario,String par_Tall,String par_Peso,String par_IMC) {
        String selectQuery = "UPDATE usuario SET " + " talla = " + par_Tall +" , "+ " peso  = " + par_Peso +" , "+ " imc   = " + par_IMC  + " WHERE (codigo="+Integer.toString(usuario)+")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
        db.close();
    }

    public String CCC="";
    public String getInformacionUsuario(int usuario) {
        String Salida="";
        if(usuario>0)
        {   String selectQuery = "SELECT * FROM usuario WHERE codigo="+ Integer.toString(usuario);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                CCC = cursor.getString(0);
                Salida= "<B>Codigo</B> : " + cursor.getString(0) + "<br />" +
                        "<B>Nombre</B> : " + cursor.getString(3) + "<br />" +
                        "<B>Nick</B>   : " + cursor.getString(4) + "<br />" +
                        "<B>Passwd</B> : " + cursor.getString(5) + "<br />" +
                        "<B>Talla</B>  : " + cursor.getString(8) + "<br />" +
                        "<B>Peso</B>   : " + cursor.getString(9)+ "<br />" +
                        "<B>IMC</B>    : " + cursor.getString(10);
            }
            cursor.close();
            db.close();
        }
        return Salida;
    }

    public void guardarAlimento(String nombre, double factor_cho, double factor_fibra, int indice_glicemico, double factor_grasa, double factor_proteina){
        String selectQuery ="INSERT INTO alimentos(nombre,tipo,factorcho,factorfibra,indiceglicemico,factorgrasa,factorproteina,observaciones,editar) VALUES ('"+nombre+"',10,"+Double.toString(factor_cho)+","+Double.toString(factor_fibra)+"," +Integer.toString(indice_glicemico)+","+Double.toString(factor_grasa)+","+Double.toString(factor_proteina)+",'',0.0);";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
        db.close();
    }

    public void modificarAlimento(int codigo, double factor_cho, double factor_fibra, int indice_glicemico, double factor_grasa, double factor_proteina){
        String selectQuery ="UPDATE alimentos SET factorcho="+Double.toString(factor_cho)+",factorfibra="+Double.toString(factor_fibra)+",indiceglicemico="+Integer.toString(indice_glicemico)+",factorgrasa="+Double.toString(factor_grasa)+",factorproteina="+Double.toString(factor_proteina)+" WHERE codigo="+Integer.toString(codigo)+";";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
        db.close();
    }

    public void guardarAlimentoTemporal(int codigo, double por){
        String a="";
        String b="";
        String c="";
        String d="";
        String e="";
        String f="";
        String g="";
        String h="";

        String selectQuery = "SELECT * FROM alimentos WHERE codigo="+ Integer.toString(codigo);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            a = cursor.getString(0);
            b = "\""+cursor.getString(1)+"\"";
            c = Double.toString(por);
            d = cursor.getString(3);
            e = cursor.getString(4);
            f = cursor.getString(5);
            g = cursor.getString(6);
            h = cursor.getString(7);
        }
        cursor.close();
        db.close();
        String selectQuery2 = "INSERT INTO temporalmenu VALUES("+a+","+b+","+c+","+d+","+e+","+f+","+g+","+h+");";
        SQLiteDatabase db2 = this.getWritableDatabase();
        db2.execSQL(selectQuery2);
        db2.close();
    }

    public void deleteAlimentosTemporal(){
        String selectQuery = "DELETE FROM temporalmenu";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
        db.close();
    }

    public void borrarAlimentoTemp(int codigoAlimento){
        if (codigoAlimento>0)
        {   String selectQuery = "DELETE FROM temporalmenu WHERE codigo="+Integer.toString(codigoAlimento);
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(selectQuery);
            db.close();
        }
    }

    public double getSencibilidad(int usuario, int hora){
        double SENSIBILIDAD=0;
        if(usuario>0)
        {   String selectQuery = "SELECT parametrosmedicos_sencibilidad FROM parametrosmedicos WHERE ( (parametrosmedicos_usuario="+Integer.toString(usuario)+") AND (parametrosmedicos_hora="+Integer.toString(hora)+") )";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                SENSIBILIDAD = Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            db.close();
        }
        return SENSIBILIDAD;
    }

    public double getRatio(int usuario, int hora){
        double ratio=0;
        if(usuario>0)
        {   String selectQuery = "SELECT parametrosmedicos_ratio FROM parametrosmedicos WHERE ( (parametrosmedicos_usuario="+Integer.toString(usuario)+") AND (parametrosmedicos_hora="+Integer.toString(hora)+") )";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                ratio = Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            db.close();
        }
        return ratio;
    }

}

