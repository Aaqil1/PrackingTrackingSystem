import axios from 'axios';
import { serviceUrls } from './config';

export const parkingClient = axios.create({ baseURL: `${serviceUrls.parking}/parking` });
export const routingClient = axios.create({ baseURL: `${serviceUrls.routing}/routing` });
export const allocationClient = axios.create({ baseURL: `${serviceUrls.allocation}/allocation` });
export const jobsClient = axios.create({ baseURL: `${serviceUrls.jobs}/jobs` });
export const telemetryClient = axios.create({ baseURL: `${serviceUrls.telemetry}/telemetry` });
export const driverCollabClient = axios.create({ baseURL: `${serviceUrls.driverCollaboration}/driver-swaps` });
