export interface Activity {
  id: number;
  type: 'PICO' | 'LIO' | 'MAMADA' | 'SEXO' | 'ORAL' | 'ANAL';
  points: number;
  timestamp: string | Date;
  notes: string | null;
}
