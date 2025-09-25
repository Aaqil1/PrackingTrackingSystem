const baseHost = import.meta.env.VITE_API_BASE_URL || 'http://localhost';

export const serviceUrls = {
  parking: `${baseHost}:8081`,
  routing: `${baseHost}:8082`,
  allocation: `${baseHost}:8083`,
  jobs: `${baseHost}:8084`,
  telemetry: `${baseHost}:8085`,
  driverCollaboration: `${baseHost}:8086`
};
