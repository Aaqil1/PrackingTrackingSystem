import { useEffect, useMemo, useState } from 'react';
import { telemetryClient } from '../api';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Tooltip,
  Legend
} from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip, Legend);

interface ConsumptionRecord {
  id: number;
  vehicleId: string;
  metricName: string;
  expectedDays: number;
  actualDays: number;
  lastCheckedDate: string;
  anomaly: boolean;
}

interface ConsumptionAnomaly {
  recordId: number;
  vehicleId: string;
  metricName: string;
  expectedDays: number;
  actualDays: number;
  deviationDays: number;
}

export default function TelemetryDashboard() {
  const [records, setRecords] = useState<ConsumptionRecord[]>([]);
  const [anomalies, setAnomalies] = useState<ConsumptionAnomaly[]>([]);

  const loadData = async () => {
    const [recordsResponse, anomalyResponse] = await Promise.all([
      telemetryClient.get<ConsumptionRecord[]>('/records'),
      telemetryClient.get<ConsumptionAnomaly[]>('/anomalies')
    ]);
    setRecords(recordsResponse.data);
    setAnomalies(anomalyResponse.data);
  };

  useEffect(() => {
    loadData();
  }, []);

  const chartData = useMemo(() => {
    if (records.length === 0) {
      return undefined;
    }
    const labels = records.map((record) => `${record.vehicleId} · ${record.metricName}`);
    return {
      labels,
      datasets: [
        {
          label: 'Expected days',
          data: records.map((record) => record.expectedDays),
          backgroundColor: '#93c5fd'
        },
        {
          label: 'Actual days',
          data: records.map((record) => record.actualDays),
          backgroundColor: '#3b82f6'
        }
      ]
    };
  }, [records]);

  return (
    <main>
      <h1>Vehicle Consumption Analytics</h1>
      <p>Detect over-consumption trends such as coolant usage dropping from 30 to 15 days.</p>

      <section>
        <h2>Usage Baselines vs Actuals</h2>
        {chartData ? <Bar data={chartData} /> : <p>No telemetry data loaded.</p>}
      </section>

      <section>
        <h2>Anomalies</h2>
        <table className="table">
          <thead>
            <tr>
              <th>Vehicle</th>
              <th>Metric</th>
              <th>Expected Days</th>
              <th>Actual Days</th>
              <th>Deviation</th>
            </tr>
          </thead>
          <tbody>
            {anomalies.map((anomaly) => (
              <tr key={anomaly.recordId}>
                <td>{anomaly.vehicleId}</td>
                <td>{anomaly.metricName}</td>
                <td>{anomaly.expectedDays}</td>
                <td style={{ color: anomaly.actualDays < anomaly.expectedDays ? 'crimson' : undefined }}>
                  {anomaly.actualDays}
                </td>
                <td>{anomaly.deviationDays} days earlier</td>
              </tr>
            ))}
            {anomalies.length === 0 && (
              <tr>
                <td colSpan={5}>No anomalies detected.</td>
              </tr>
            )}
          </tbody>
        </table>
      </section>

      <section>
        <h2>Latest Records</h2>
        <table className="table">
          <thead>
            <tr>
              <th>Vehicle</th>
              <th>Metric</th>
              <th>Expected</th>
              <th>Actual</th>
              <th>Checked</th>
              <th>Flag</th>
            </tr>
          </thead>
          <tbody>
            {records.map((record) => (
              <tr key={record.id}>
                <td>{record.vehicleId}</td>
                <td>{record.metricName}</td>
                <td>{record.expectedDays}</td>
                <td>{record.actualDays}</td>
                <td>{record.lastCheckedDate}</td>
                <td>{record.anomaly ? <span className="badge">Investigate</span> : 'OK'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
    </main>
  );
}
