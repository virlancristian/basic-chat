const API_PORT: string = process.env.REACT_APP_BACKEND_SERVER_PORT || '8080';
export const API_URL:string = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${API_PORT}`;