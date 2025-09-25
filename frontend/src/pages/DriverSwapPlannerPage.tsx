import { FormEvent, useEffect, useState } from 'react';
import { driverCollabClient } from '../api';

interface DriverSwapPlan {
  id: number;
  originBranch: string;
  destinationBranch: string;
  departureDate: string;
  driversInvolved: string;
  shiftStartTime: string;
  shiftEndTime: string;
  returnTripStartTime: string;
  savedWaitTimeHours: number;
  savedWaitCost: number;
  notes: string;
}

interface SummaryDto {
  plannedTrips: number;
  totalSavedHours: number;
  totalSavedCost: number;
}

export default function DriverSwapPlannerPage() {
  const [plans, setPlans] = useState<DriverSwapPlan[]>([]);
  const [form, setForm] = useState({
    originBranch: '',
    destinationBranch: '',
    departureDate: new Date().toISOString().slice(0, 10),
    driversInvolved: '',
    shiftStartTime: '07:00',
    shiftEndTime: '15:00',
    returnTripStartTime: '16:00',
    originalWaitTimeHours: 3,
    hourlyRate: 50,
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
      setForm({
        ...form,
        originBranch: '',
        destinationBranch: '',
        driversInvolved: '',
        notes: ''
      });
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
          <label>
            Shift start
            <input
              type="time"
              value={form.shiftStartTime}
              onChange={(event) => setForm({ ...form, shiftStartTime: event.target.value })}
              required
            />
          </label>
          <label>
            Shift end
            <input
              type="time"
              value={form.shiftEndTime}
              onChange={(event) => setForm({ ...form, shiftEndTime: event.target.value })}
              required
            />
          </label>
          <label>
            Return trip start
            <input
              type="time"
              value={form.returnTripStartTime}
              onChange={(event) => setForm({ ...form, returnTripStartTime: event.target.value })}
              required
            />
          </label>
          <label>
            Original idle (hrs)
            <input
              type="number"
              min={0}
              step={0.5}
              value={form.originalWaitTimeHours}
              onChange={(event) => setForm({ ...form, originalWaitTimeHours: Number(event.target.value) })}
              required
            />
          </label>
          <label>
            Hourly rate ($)
            <input
              type="number"
              min={0}
              step={1}
              value={form.hourlyRate}
              onChange={(event) => setForm({ ...form, hourlyRate: Number(event.target.value) })}
              required
            />
          </label>
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
              <th>Shift</th>
              <th>Return start</th>
              <th>Saved Hours</th>
              <th>Saved Cost</th>
              <th>Notes</th>
            </tr>
          </thead>
          <tbody>
            {plans.map((plan) => (
              <tr key={plan.id}>
                <td>{plan.departureDate}</td>
                <td>{plan.originBranch} → {plan.destinationBranch}</td>
                <td>{plan.driversInvolved}</td>
                <td>{plan.shiftStartTime} - {plan.shiftEndTime}</td>
                <td>{plan.returnTripStartTime}</td>
                <td>{plan.savedWaitTimeHours.toFixed(1)}</td>
                <td>${plan.savedWaitCost.toFixed(2)}</td>
                <td>{plan.notes}</td>
              </tr>
            ))}
            {plans.length === 0 && (
              <tr>
                <td colSpan={7}>No swaps scheduled.</td>
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
            {' '}That equates to{' '}
            <strong>${summary.totalSavedCost.toFixed(2)}</strong> saved at current hourly rates.
          </p>
        </section>
      )}
    </main>
  );
}
