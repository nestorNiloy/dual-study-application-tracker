/**
 * api.js — DualTrack v2
 * All backend communication in one place.
 * Talks to the Spring Boot REST API at API_BASE.
 */

const API_BASE = 'https://dual-study-application-tracker.onrender.com/api/applications';

const api = {

  async getAll() {
    const res = await fetch(API_BASE);
    if (!res.ok) throw new Error(`GET failed: ${res.status}`);
    return res.json();
  },

  async create(payload) {
    const res = await fetch(API_BASE, {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify(payload),
    });
    if (!res.ok) {
      const body = await res.text();
      throw new Error(body || `POST failed: ${res.status}`);
    }
    return res.json();
  },

  async remove(id) {
    const res = await fetch(`${API_BASE}/${id}`, { method: 'DELETE' });
    if (!res.ok && res.status !== 204) throw new Error(`DELETE failed: ${res.status}`);
  },

};
