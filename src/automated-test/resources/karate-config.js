function fn() {
  let config = {};
  let baseUrl = java.lang.System.getenv('BASE_URL'); // Obtiene la variable de entorno BASE_URL

  if (!baseUrl) {
    baseUrl = 'http://localhost:8080'; // Valor por defecto si no se especifica la variable
  }

  config.baseUrl = baseUrl + '/api'; // Configura la URL base para las pruebas

  return config;
}