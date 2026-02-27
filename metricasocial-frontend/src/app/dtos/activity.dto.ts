export interface ActivityDTO {
  type: string;
  points: number;
  timestamp?: string | Date;
  notes?: string;
}

export interface ActivityResponseDTO {
  id: number;
  type: string;
  points: number;
  timestamp: string | Date;
  notes: string | null;
}
