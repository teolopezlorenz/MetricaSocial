export interface UserBadge {
  id: number;
  badge: Badge;
  achievedAt: string | Date;
}

export interface Badge {
  id: number;
  name: string;
  description: string;
  pointsRequired: number;
}
