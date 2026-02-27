export interface Friend {
  id: number;
  username: string;
  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  createdAt: string | Date;
  updatedAt: string | Date | null;
}
