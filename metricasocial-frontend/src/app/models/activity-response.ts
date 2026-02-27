export interface ActivityResponse {
  id: number;
  type: string;
  points: number;
  timestamp: string | Date;
  notes: string | null;
}
