import { FormEvent, useEffect, useState } from 'react';
import { allocationClient } from '../api';

interface Vehicle {
  id: number;
  registrationNumber: string;
  model: string;
  maxVolume: number;
  maxQuantity: number;
}

export default function VehicleOnboardingPage() {
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [form, setForm] = useState({ registrationNumber: '', model: '', maxVolume: 80, maxQuantity: 120 });
  const [message, setMessage] = useState('');

  const loadVehicles = async () => {
    const response = await allocationClient.get<Vehicle[]>('/vehicles');
    setVehicles(response.data);
  };

  useEffect(() => {
    loadVehicles();
  }, []);

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setMessage('');
    try {
      await allocationClient.post('/vehicles', form);
      setMessage('Vehicle onboarded successfully.');
      setForm({ registrationNumber: '', model: '', maxVolume: 80, maxQuantity: 120 });
      await loadVehicles();
    } catch (error: any) {
      setMessage(error.response?.data?.error || 'Unable to onboard vehicle.');
    }
  };

  return (
    <main>
      <h1>Vehicle Onboarding</h1>
      <p>Register vehicles with capacity data to enable automatic allocation.</p>

      {message && <section><strong>{message}</strong></section>}

      <section>
        <h2>New Vehicle</h2>
        <form onSubmit={handleSubmit}>
          <input
            placeholder="Registration number"
            value={form.registrationNumber}
            onChange={(event) => setForm({ ...form, registrationNumber: event.target.value })}
            required
          />
          <input
            placeholder="Model"
            value={form.model}
            onChange={(event) => setForm({ ...form, model: event.target.value })}
            required
          />
          <input
            type="number"
            placeholder="Max volume (m³)"
            value={form.maxVolume}
            min={1}
            onChange={(event) => setForm({ ...form, maxVolume: Number(event.target.value) })}
            required
          />
          <input
            type="number"
            placeholder="Max quantity"
            value={form.maxQuantity}
            min={1}
            onChange={(event) => setForm({ ...form, maxQuantity: Number(event.target.value) })}
            required
          />
          <button type="submit">Onboard Vehicle</button>
        </form>
      </section>

      <section>
        <h2>Active Fleet</h2>
        <table className="table">
          <thead>
            <tr>
              <th>Registration</th>
              <th>Model</th>
              <th>Max Volume</th>
              <th>Max Quantity</th>
            </tr>
          </thead>
          <tbody>
            {vehicles.map((vehicle) => (
              <tr key={vehicle.id}>
                <td>{vehicle.registrationNumber}</td>
                <td>{vehicle.model}</td>
                <td>{vehicle.maxVolume}</td>
                <td>{vehicle.maxQuantity}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
    </main>
  );
}
