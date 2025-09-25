import { FormEvent, useEffect, useState } from 'react';
import { driverCollabClient } from '../api';

interface DriverSwapPlan {
  id: number;
  originBranch: string;
  destinationBranch: string;
  departureDate: string;
  driversInvolved: string;
  savedWaitTimeHours: number;
  notes: string;
}

interface SummaryDto {
  plannedTrips: number;
  totalSavedHours: number;
}

export default function DriverSwapPlannerPage() {
  const [plans, setPlans] = useState<DriverSwapPlan[]>([]);
  const [form, setForm] = useState({
    originBranch: '',
    destinationBranch: '',
    departureDate: new Date().toISOString().slice(0, 10),
    driversInvolved: '',
    savedWaitTimeHours: 0,
    notes: ''
  });
  const [summary, setSummary] = useState<SummaryDto | null>(null);
  const [message, setMessage] = useState('');

  const loadPlans = async () => {
    const response = await driverCollabClient.get<DriverSwapPlan[]>('');
    setPlans(response.data);
  };

  const loadSummary = async () => {
    const start = new Date();
    const end = new Date();
    end.setDate(start.getDate() + 7);
    const response = await driverCollabClient.get<SummaryDto>(`/summary`, {
      params: {
        start: start.toISOString().slice(0, 10),
        end: end.toISOString().slice(0, 10)
      }
    });
    setSummary(response.data);
  };

  useEffect(() => {
    loadPlans();
    loadSummary();
  }, []);

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setMessage('');
    try {
      await driverCollabClient.post('', form);
      setMessage('Driver swap planned.');
      setForm({ ...form, originBranch: '', destinationBranch: '', driversInvolved: '', savedWaitTimeHours: 0, notes: '' });
      await loadPlans();
      await loadSummary();
    } catch (err: any) {
      setMessage(err.response?.data?.error || 'Unable to plan driver swap.');
    }
  };

  return (
    <main>
      <h1>Driver Swap Planner</h1>
      <p>Coordinate branch collaboration, return trips, and track wait-time savings.</p>

      {message && <section><strong>{message}</strong></section>}

      <section>
        <h2>New Swap Plan</h2>
        <form onSubmit={handleSubmit}>
          <input
            placeholder="Origin branch"
            value={form.originBranch}
            onChange={(event) => setForm({ ...form, originBranch: event.target.value })}
            required
          />
          <input
            placeholder="Destination branch"
            value={form.destinationBranch}
            onChange={(event) => setForm({ ...form, destinationBranch: event.target.value })}
            required
          />
          <input
            type="date"
            value={form.departureDate}
            onChange={(event) => setForm({ ...form, departureDate: event.target.value })}
            required
          />
          <input
            placeholder="Drivers involved"
            value={form.driversInvolved}
            onChange={(event) => setForm({ ...form, driversInvolved: event.target.value })}
            required
          />
          <input
            type="number"
            placeholder="Saved wait time (hours)"
            value={form.savedWaitTimeHours}
            min={0}
            step={0.5}
            onChange={(event) => setForm({ ...form, savedWaitTimeHours: Number(event.target.value) })}
          />
          <input
            placeholder="Notes"
            value={form.notes}
            onChange={(event) => setForm({ ...form, notes: event.target.value })}
          />
          <button type="submit">Plan Swap</button>
        </form>
      </section>

      <section>
        <h2>Upcoming Swaps</h2>
        <table className="table">
          <thead>
            <tr>
              <th>Date</th>
              <th>Route</th>
              <th>Drivers</th>
              <th>Saved Hours</th>
              <th>Notes</th>
            </tr>
          </thead>
          <tbody>
            {plans.map((plan) => (
              <tr key={plan.id}>
                <td>{plan.departureDate}</td>
                <td>{plan.originBranch} → {plan.destinationBranch}</td>
                <td>{plan.driversInvolved}</td>
                <td>{plan.savedWaitTimeHours}</td>
                <td>{plan.notes}</td>
              </tr>
            ))}
            {plans.length === 0 && (
              <tr>
                <td colSpan={5}>No swaps scheduled.</td>
              </tr>
            )}
          </tbody>
        </table>
      </section>

      {summary && (
        <section>
          <h2>Next 7 Days Impact</h2>
          <p>
            <strong>{summary.plannedTrips}</strong> swap trips planned with{' '}
            <strong>{summary.totalSavedHours.toFixed(1)} hours</strong> of driver wait time saved.
          </p>
        </section>
      )}
    </main>
  );
}
