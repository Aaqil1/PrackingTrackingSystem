import { FormEvent, useEffect, useState } from 'react';
import { jobsClient } from '../api';

interface JobRecord {
  id: number;
  vehicleId: string;
  description: string;
  goodsVolume: number;
  jobDate: string;
  status: string;
}

export default function JobHistoryPage() {
  const [jobs, setJobs] = useState<JobRecord[]>([]);
  const [selectedJob, setSelectedJob] = useState<JobRecord | null>(null);
  const [form, setForm] = useState({ vehicleId: '', description: '', goodsVolume: 20, jobDate: '', status: 'PLANNED' });
  const [message, setMessage] = useState('');

  const loadJobs = async () => {
    const response = await jobsClient.get<JobRecord[]>('');
    setJobs(response.data);
    if (response.data.length > 0) {
      setSelectedJob(response.data[0]);
    }
  };

  useEffect(() => {
    loadJobs();
  }, []);

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setMessage('');
    try {
      const response = await jobsClient.post<JobRecord>('', form);
      setMessage('Job recorded.');
      setSelectedJob(response.data);
      setForm({ vehicleId: '', description: '', goodsVolume: 20, jobDate: '', status: 'PLANNED' });
      await loadJobs();
    } catch (err: any) {
      setMessage(err.response?.data?.error || 'Unable to record job.');
    }
  };

  return (
    <main>
      <h1>Job History</h1>
      <p>Log and review completed and planned transport jobs.</p>

      {message && <section><strong>{message}</strong></section>}

      <section>
        <h2>Create Job Entry</h2>
        <form onSubmit={handleSubmit}>
          <input
            placeholder="Vehicle ID"
            value={form.vehicleId}
            onChange={(event) => setForm({ ...form, vehicleId: event.target.value })}
            required
          />
          <input
            placeholder="Description"
            value={form.description}
            onChange={(event) => setForm({ ...form, description: event.target.value })}
            required
          />
          <input
            type="number"
            placeholder="Goods volume"
            value={form.goodsVolume}
            min={0}
            onChange={(event) => setForm({ ...form, goodsVolume: Number(event.target.value) })}
          />
          <input
            type="date"
            value={form.jobDate}
            onChange={(event) => setForm({ ...form, jobDate: event.target.value })}
            required
          />
          <select value={form.status} onChange={(event) => setForm({ ...form, status: event.target.value })}>
            <option value="PLANNED">Planned</option>
            <option value="IN_PROGRESS">In Progress</option>
            <option value="COMPLETED">Completed</option>
          </select>
          <button type="submit">Record Job</button>
        </form>
      </section>

      <section>
        <h2>Job Log</h2>
        <div style={{ display: 'grid', gridTemplateColumns: '2fr 3fr', gap: '1.5rem' }}>
          <table className="table">
            <thead>
              <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {jobs.map((job) => (
                <tr key={job.id} onClick={() => setSelectedJob(job)} style={{ cursor: 'pointer' }}>
                  <td>{job.jobDate}</td>
                  <td>{job.description}</td>
                  <td>{job.status}</td>
                </tr>
              ))}
            </tbody>
          </table>

          {selectedJob && (
            <div>
              <h3>Job Details</h3>
              <p><strong>Vehicle:</strong> {selectedJob.vehicleId}</p>
              <p><strong>Description:</strong> {selectedJob.description}</p>
              <p><strong>Goods Volume:</strong> {selectedJob.goodsVolume} m³</p>
              <p><strong>Date:</strong> {selectedJob.jobDate}</p>
              <p><strong>Status:</strong> {selectedJob.status}</p>
            </div>
          )}
        </div>
      </section>
    </main>
  );
}
