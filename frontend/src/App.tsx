import { NavLink, Route, Routes } from 'react-router-dom';
import ParkingDashboard from './pages/ParkingDashboard';
import VehicleOnboardingPage from './pages/VehicleOnboardingPage';
import GoodsAllocationPage from './pages/GoodsAllocationPage';
import JobHistoryPage from './pages/JobHistoryPage';
import TelemetryDashboard from './pages/TelemetryDashboard';
import DriverSwapPlannerPage from './pages/DriverSwapPlannerPage';

export default function App() {
  return (
    <div>
      <nav>
        <NavLink to="/" end>Parking</NavLink>
        <NavLink to="/vehicles">Vehicles</NavLink>
        <NavLink to="/allocation">Auto-allotment</NavLink>
        <NavLink to="/jobs">Job History</NavLink>
        <NavLink to="/telemetry">Telemetry</NavLink>
        <NavLink to="/driver-swaps">Driver Swaps</NavLink>
      </nav>
      <Routes>
        <Route path="/" element={<ParkingDashboard />} />
        <Route path="/vehicles" element={<VehicleOnboardingPage />} />
        <Route path="/allocation" element={<GoodsAllocationPage />} />
        <Route path="/jobs" element={<JobHistoryPage />} />
        <Route path="/telemetry" element={<TelemetryDashboard />} />
        <Route path="/driver-swaps" element={<DriverSwapPlannerPage />} />
      </Routes>
    </div>
  );
}
