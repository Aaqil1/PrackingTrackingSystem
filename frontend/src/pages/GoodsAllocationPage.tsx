import { FormEvent, useState } from 'react';
import { allocationClient } from '../api';

interface AutoAllocationResponse {
  vehicle: {
    id: number;
    registrationNumber: string;
    model: string;
    maxVolume: number;
    maxQuantity: number;
  };
  allocationComment: string;
}

export default function GoodsAllocationPage() {
  const [form, setForm] = useState({ goodsVolume: 50, goodsQuantity: 80 });
  const [result, setResult] = useState<AutoAllocationResponse | null>(null);
  const [error, setError] = useState('');

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setError('');
    setResult(null);
    try {
      const response = await allocationClient.post<AutoAllocationResponse>('/auto', form);
      setResult(response.data);
    } catch (err: any) {
      setError(err.response?.data?.error || 'Unable to auto-allocate vehicle.');
    }
  };

  return (
    <main>
      <h1>Goods → Vehicle Auto-allotment</h1>
      <p>Enter goods volume and quantity to receive the most appropriate vehicle suggestion.</p>

      <section>
        <h2>Request Allocation</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="number"
            placeholder="Goods volume (m³)"
            value={form.goodsVolume}
            min={1}
            onChange={(event) => setForm({ ...form, goodsVolume: Number(event.target.value) })}
            required
          />
          <input
            type="number"
            placeholder="Goods quantity"
            value={form.goodsQuantity}
            min={1}
            onChange={(event) => setForm({ ...form, goodsQuantity: Number(event.target.value) })}
            required
          />
          <button type="submit">Find Vehicle</button>
        </form>
        {error && <p style={{ color: 'crimson' }}>{error}</p>}
      </section>

      {result && (
        <section>
          <h2>Recommended Vehicle</h2>
          <p>{result.allocationComment}</p>
          <ul>
            <li><strong>Registration:</strong> {result.vehicle.registrationNumber}</li>
            <li><strong>Model:</strong> {result.vehicle.model}</li>
            <li><strong>Capacity:</strong> {result.vehicle.maxVolume} m³ · {result.vehicle.maxQuantity} units</li>
          </ul>
        </section>
      )}
    </main>
  );
}
