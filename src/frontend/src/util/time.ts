export function getDate(): string {
    const dateObject: Date = new Date();
    const year: number = dateObject.getFullYear();
    const month: number = dateObject.getMonth() + 1;
    const day: number = dateObject.getDate();

    const monthString: string = month < 10 ? `0${month}` : `${month}`;
    const dayString: string = day < 10 ? `0${day}` : `${day}`;

    return `${year}-${monthString}-${dayString}`;
}

export function getHour(): string {
    const dateObject: Date = new Date();
    const hour: number = dateObject.getHours();
    const minutes: number = dateObject.getMinutes();
    const seconds: number = dateObject.getSeconds();
    const ms: number = dateObject.getMilliseconds();

    const hourString: string = hour < 10 ? `0${hour}` : `${hour}`;
    const minutesString: string = minutes < 10 ? `0${minutes}` : `${minutes}`;
    const secondsString: string = seconds < 10 ? `0${seconds}` : `${seconds}`;

    return `${hourString}:${minutesString}:${secondsString}.${ms}`;
}