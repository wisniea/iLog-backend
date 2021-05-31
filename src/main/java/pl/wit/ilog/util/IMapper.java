package pl.wit.ilog.util;

public interface IMapper<SRC,DST> {
    DST map(SRC src);
}
