package dn.ftc.data;

public record MeasurementData(float centeredLength, float force, long timestamp) implements DataPack{
}
