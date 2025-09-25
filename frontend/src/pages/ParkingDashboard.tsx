import { useEffect, useMemo, useState } from 'react';
import { parkingClient, routingClient } from '../api';

interface ParkingSlot {
  id: number;
  label: string;
  size: 'SMALL' | 'MEDIUM' | 'LARGE';
  occupied: boolean;
}

interface ParkingLot {
  id: number;
  name: string;
  city: string;
  area: string;
  occupancyRate: number;
  needAdditionalSlots: boolean;
  slots: ParkingSlot[];
}

type SlotSize = 'SMALL' | 'MEDIUM' | 'LARGE';

interface RoutingSuggestion {
  lotName: string;
  area: string;
  distanceKm: number;
  availableSlots: number;
  supportedSizes: string;
}

const slotSizes: SlotSize[] = ['SMALL', 'MEDIUM', 'LARGE'];

export default function ParkingDashboard() {
  const [lots, setLots] = useState<ParkingLot[]>([]);
  const [loading, setLoading] = useState(false);
  const [arrivalLot, setArrivalLot] = useState<number | ''>('');
  const [arrivalSize, setArrivalSize] = useState<SlotSize>('MEDIUM');
  const [departureLot, setDepartureLot] = useState<number | ''>('');
  const [departureSlot, setDepartureSlot] = useState<number | ''>('');
  const [message, setMessage] = useState('');
  const [routingMessage, setRoutingMessage] = useState('');
  const [routingPayload, setRoutingPayload] = useState({ city: '', area: '', size: 'MEDIUM', distance: 10 });

  const departureSlots = useMemo(() => {
    const lot = lots.find((l) => l.id === departureLot);
    return lot ? lot.slots.filter((slot) => slot.occupied) : [];
  }, [departureLot, lots]);

  const loadLots = async () => {
    setLoading(true);
    try {
      const response = await parkingClient.get<ParkingLot[]>('/lots');
      setLots(response.data);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadLots();
  }, []);

  const handleArrival = async () => {
    if (!arrivalLot) return;
    setMessage('');
    try {
      const response = await parkingClient.post(`/lots/${arrivalLot}/arrival`, { vehicleSize: arrivalSize });
      setMessage(`Allocated slot ${response.data.label} for ${arrivalSize} vehicle.`);
      await loadLots();
    } catch (error: any) {
      setMessage(error.response?.data?.error || 'Unable to allocate slot.');
    }
  };

  const handleDeparture = async () => {
    if (!departureLot || !departureSlot) return;
    setMessage('');
    try {
      await parkingClient.post(`/lots/${departureLot}/departure`, { slotId: departureSlot });
      setMessage('Slot released successfully.');
      await loadLots();
    } catch (error: any) {
      setMessage(error.response?.data?.error || 'Unable to release slot.');
    }
  };

  const requestPrediction = async (lotId: number) => {
    const response = await parkingClient.get(`/lots/${lotId}/prediction`);
    const { needAdditionalSlots, occupancyRate } = response.data;
    setMessage(
      `Prediction for lot ${lotId}: occupancy ${(occupancyRate * 100).toFixed(0)}% — ` +
        (needAdditionalSlots ? 'rent more slots recommended.' : 'capacity sufficient.')
    );
  };

  const handleRouting = async () => {
    setRoutingMessage('');
    try {
      const response = await routingClient.post<RoutingSuggestion>('/suggestions', {
        currentCity: routingPayload.city,
        preferredArea: routingPayload.area,
        vehicleSize: routingPayload.size,
        maximumDistanceKm: routingPayload.distance
      });
      const suggestion = response.data;
      setRoutingMessage(
        `Route to ${suggestion.lotName} (${suggestion.area}), ${suggestion.distanceKm.toFixed(1)} km away with ${suggestion.availableSlots} free slots.`
      );
    } catch (error: any) {
      setRoutingMessage(error.response?.data?.error || 'No routing option available.');
    }
  };

  return (
    <main>
      <h1>Parking & Routing</h1>
      <p>Monitor occupancy, allocate slots on arrival/departure, and anticipate extra space requirements.</p>

      {message && <section><strong>{message}</strong></section>}
      {routingMessage && <section><strong>{routingMessage}</strong></section>}

      <section>
        <h2>Parking Lots Overview</h2>
        {loading ? (
          <p>Loading lots...</p>
        ) : (
          <div className="card-grid">
            {lots.map((lot) => (
              <div key={lot.id}>
                <h3>{lot.name}</h3>
                <p>{lot.city} · {lot.area}</p>
                <p>Occupancy: {(lot.occupancyRate * 100).toFixed(0)}%</p>
                <p>
                  Status:{' '}
                  <span className="badge">{lot.needAdditionalSlots ? 'Consider more slots' : 'Capacity OK'}</span>
                </p>
                <button onClick={() => requestPrediction(lot.id)}>Refresh prediction</button>
                <table className="table" style={{ marginTop: '1rem' }}>
                  <thead>
                    <tr>
                      <th>Slot</th>
                      <th>Size</th>
                      <th>Occupied</th>
                    </tr>
                  </thead>
                  <tbody>
                    {lot.slots.map((slot) => (
                      <tr key={slot.id}>
                        <td>{slot.label}</td>
                        <td>{slot.size}</td>
                        <td>{slot.occupied ? 'Yes' : 'No'}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            ))}
          </div>
        )}
      </section>

      <section>
        <h2>Vehicle Arrival Allocation</h2>
        <form
          onSubmit={(event) => {
            event.preventDefault();
            handleArrival();
          }}
        >
          <select value={arrivalLot} onChange={(event) => setArrivalLot(Number(event.target.value))} required>
            <option value="">Select parking lot</option>
            {lots.map((lot) => (
              <option key={lot.id} value={lot.id}>
                {lot.name}
              </option>
            ))}
          </select>
          <select value={arrivalSize} onChange={(event) => setArrivalSize(event.target.value as SlotSize)}>
            {slotSizes.map((size) => (
              <option key={size} value={size}>
                {size}
              </option>
            ))}
          </select>
          <button type="submit">Allocate Slot</button>
        </form>
      </section>

      <section>
        <h2>Vehicle Departure</h2>
        <form
          onSubmit={(event) => {
            event.preventDefault();
            handleDeparture();
          }}
        >
          <select value={departureLot} onChange={(event) => setDepartureLot(Number(event.target.value))} required>
            <option value="">Select parking lot</option>
            {lots.map((lot) => (
              <option key={lot.id} value={lot.id}>
                {lot.name}
              </option>
            ))}
          </select>
          <select value={departureSlot} onChange={(event) => setDepartureSlot(Number(event.target.value))} required>
            <option value="">Occupied slot</option>
            {departureSlots.map((slot) => (
              <option key={slot.id} value={slot.id}>
                {slot.label} ({slot.size})
              </option>
            ))}
          </select>
          <button type="submit">Release Slot</button>
        </form>
      </section>

      <section>
        <h2>Re-route Vehicle to Best Lot</h2>
        <form
          onSubmit={(event) => {
            event.preventDefault();
            handleRouting();
          }}
        >
          <input
            placeholder="Current city"
            value={routingPayload.city}
            onChange={(event) => setRoutingPayload({ ...routingPayload, city: event.target.value })}
            required
          />
          <input
            placeholder="Preferred area"
            value={routingPayload.area}
            onChange={(event) => setRoutingPayload({ ...routingPayload, area: event.target.value })}
          />
          <select
            value={routingPayload.size}
            onChange={(event) => setRoutingPayload({ ...routingPayload, size: event.target.value })}
          >
            {slotSizes.map((size) => (
              <option key={size} value={size}>
                {size}
              </option>
            ))}
          </select>
          <input
            type="number"
            placeholder="Max distance (km)"
            value={routingPayload.distance}
            onChange={(event) => setRoutingPayload({ ...routingPayload, distance: Number(event.target.value) })}
            min={1}
          />
          <button type="submit">Suggest Parking</button>
        </form>
      </section>
    </main>
  );
}
