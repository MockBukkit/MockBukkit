package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.proxy.AutoConvert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AutoConvert
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MockLocation
{
	private double x;
	private double y;
}
