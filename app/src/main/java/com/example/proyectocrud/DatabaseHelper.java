package com.example.proyectocrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";

    private static final String COL_ID = "id";
    private static final String COL_CEDULA = "cedula";
    private static final String COL_NOMBRES = "nombres";
    private static final String COL_APELLIDOS = "apellidos";
    private static final String COL_EDAD = "edad";
    private static final String COL_ESTADO_CIVIL = "estado_civil";
    private static final String COL_NACIONALIDAD = "nacionalidad";
    private static final String COL_GENERO = "genero";
    private static final String COL_CORREO = "correo";
    private static final String COL_PASSWORD = "password";

    private static final String TABLE_CUENTAS = "cuentas";

    private static final String COL_ID_CUENTA = "id";
    private static final String COL_CEDULA_CUENTA = "cedula";
    private static final String COL_NOMBRES_CUENTA = "nombres";
    private static final String COL_APELLIDOS_CUENTA = "apellidos";
    private static final String COL_EDAD_CUENTA = "edad";
    private static final String COL_FECHA_NAC_CUENTA = "fecha_nacimiento";
    private static final String COL_SALDO_CUENTA = "saldo";
    private static final String COL_BANCO_CUENTA = "banco";
    private static final String COL_CORREO_CUENTA = "correo";
    private static final String COL_PASSWORD_CUENTA = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CEDULA + " TEXT, " +
                COL_NOMBRES + " TEXT, " +
                COL_APELLIDOS + " TEXT, " +
                COL_EDAD + " INTEGER, " +
                COL_ESTADO_CIVIL + " TEXT, " +
                COL_NACIONALIDAD + " TEXT, " +
                COL_GENERO + " TEXT, " +
                COL_CORREO + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createTable);

        String createCuentasTable = "CREATE TABLE " + TABLE_CUENTAS + " (" +
                COL_ID_CUENTA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CEDULA_CUENTA + " TEXT, " +
                COL_NOMBRES_CUENTA + " TEXT, " +
                COL_APELLIDOS_CUENTA + " TEXT, " +
                COL_EDAD_CUENTA + " INTEGER, " +
                COL_FECHA_NAC_CUENTA + " TEXT, " +
                COL_BANCO_CUENTA + " TEXT, " +
                COL_SALDO_CUENTA + " DOUBLE, " +
                COL_CORREO_CUENTA + " TEXT UNIQUE, " +
                COL_PASSWORD_CUENTA + " TEXT)";
        db.execSQL(createCuentasTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean insertUser(String cedula, String nombres, String apellidos, int edad, String estadoCivil, String nacionalidad, String genero, String correo, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CEDULA, cedula);
        values.put(COL_NOMBRES, nombres);
        values.put(COL_APELLIDOS, apellidos);
        values.put(COL_EDAD, edad);
        values.put(COL_ESTADO_CIVIL, estadoCivil);
        values.put(COL_NACIONALIDAD, nacionalidad);
        values.put(COL_GENERO, genero);
        values.put(COL_CORREO, correo);
        values.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String correo, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_CORREO + "=? AND " + COL_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{correo, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean agregarCuenta(String cedula, String nombres, String apellidos, int edad, String fechaNacimiento, String banco, Double saldo, String correo, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CEDULA_CUENTA, cedula);
        values.put(COL_NOMBRES_CUENTA, nombres);
        values.put(COL_APELLIDOS_CUENTA, apellidos);
        values.put(COL_EDAD_CUENTA, edad);
        values.put(COL_FECHA_NAC_CUENTA, fechaNacimiento);
        values.put(COL_BANCO_CUENTA, banco);
        values.put(COL_SALDO_CUENTA, saldo);
        values.put(COL_CORREO_CUENTA, correo);
        values.put(COL_PASSWORD_CUENTA, password);

        long result = db.insert(TABLE_CUENTAS, null, values);
        return result != -1;
    }
    public boolean actualizarCuenta(int id, String nombres, String apellidos, int edad, String fechaNacimiento, String banco, Double saldo, String correo, String contraseña) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOMBRES_CUENTA, nombres);
        values.put(COL_APELLIDOS_CUENTA, apellidos);
        values.put(COL_EDAD_CUENTA, edad);
        values.put(COL_FECHA_NAC_CUENTA, fechaNacimiento);
        values.put(COL_BANCO_CUENTA, banco);
        values.put(COL_SALDO_CUENTA, saldo);
        values.put(COL_CORREO_CUENTA, correo);
        values.put(COL_CORREO_CUENTA, contraseña);

        int result = db.update(TABLE_CUENTAS, values, COL_ID_CUENTA + " = ?", new String[]{String.valueOf(id)});
        return result != -1;
    }
    public boolean eliminarCuenta(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CUENTAS, COL_ID_CUENTA + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public Cursor obtenerCuentas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM cuentas", null); // Asegúrate de que la tabla exista
    }

    public Cursor obtenerCuentaPorIdEdit(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM cuentas WHERE id = ?", new String[]{String.valueOf(id)});
    }

    public Cursor obtenerCuentaPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CUENTAS + " WHERE " + COL_ID_CUENTA + " = ?", new String[]{String.valueOf(id)});
    }


}
